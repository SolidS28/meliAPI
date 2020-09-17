package item.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import item.model.Child;

@Repository
public interface ChildRepository extends CrudRepository<Child, String> {
}
