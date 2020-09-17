package item.service;

import io.micronaut.cache.CacheManager;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.reactivex.Single;
import item.model.Child;
import item.model.Item;
import item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {


    private static final String PERSISTANCE_ROUTING = "persistance.items.#";
    private static final String PERSISTANCE_TOPIC = "items-persistance";

    @Inject
    private RxHttpClient webClient;

    @Inject
    private final ItemRepository itemRepository;

//    @Inject
//    private final CacheManager<String> cacheManager;

    //private final RabbitTemplate rabbitTemplate;

    @Override
    public Single<Item> getItem(String id) {
//        Cache cache = cacheManager.getCache(CacheConfig.ITEMS_CACHE);
//        if (cache.get(id) != null) {
        Optional<Item> itemDb = itemRepository.findById(id);
//
        if (itemDb.isPresent()) {
            return Single.just(itemDb.get());
        } else {
            //cache.evict(id);
        }
//        }
        return getItemFromRest(id);

    }

    /**
     * Merge the item and
     *
     * @param id - item id
     * @return Mono reference of the merged item
     */
    private Single<Item> getItemFromRest(String id) {
        return fetchItem(id).zipWith(fetchChildren(id), (item, children) -> {
            item.setItems(children);
            sendPersistanceMessage(item);
            return item;
        });
    }

    /**
     * Fetch the item according to its id
     *
     * @param id - item id
     * @return Mono reference of the item
     */
    private Single<Item> fetchItem(String id) {
        HttpRequest<String> req = HttpRequest.GET(String.format("https://api.mercadolibre.com/items/%s", id));
        return webClient.retrieve(req, Item.class).retry(5).lastOrError();
    }

    /**
     * Fetch the children elements according to its parent's id
     *
     * @param id - parent's id
     * @return Mono reference of the list of children
     */
    private Single<List<Child>> fetchChildren(String id) {
        HttpRequest<String> req = HttpRequest.GET(String.format("https://api.mercadolibre.com/items/%s/children", id));
        return webClient.retrieve(req, Argument.listOf(Child.class)).retry(5).last(new ArrayList<Child>());
    }

    /**
     * Sends the message to persiste the item into db
     *
     * @param item - item to be persisted
     *             //@throws IOException
     */
    private void sendPersistanceMessage(Item item) {
        itemRepository.save(item);
        //rabbitTemplate.convertAndSend(PERSISTANCE_TOPIC, PERSISTANCE_ROUTING, item);
    }

}
