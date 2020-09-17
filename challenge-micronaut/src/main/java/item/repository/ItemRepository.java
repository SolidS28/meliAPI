package item.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import item.model.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, String> {
}
