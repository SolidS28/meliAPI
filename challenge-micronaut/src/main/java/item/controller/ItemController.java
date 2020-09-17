package item.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.reactivex.Single;
import item.model.Item;
import item.service.ItemService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

@RequiredArgsConstructor
@Controller("/items")
public class ItemController {

    @Inject
    private final ItemService itemService;

    @Get(uri = "/{id}")
    public Single<Item> getItem(@PathVariable String id) {
        return itemService.getItem(id);
    }
}