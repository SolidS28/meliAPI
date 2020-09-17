package item.service;

import io.reactivex.Single;
import item.model.Item;

import javax.inject.Singleton;

@Singleton
public interface ItemService {

    Single<Item> getItem(String id);
}
