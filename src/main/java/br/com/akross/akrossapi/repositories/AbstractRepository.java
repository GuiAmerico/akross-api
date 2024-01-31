package br.com.akross.akrossapi.repositories;

import br.com.akross.akrossapi.models.AbstractEntity;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@Transactional
@NoRepositoryBean
public interface AbstractRepository<T extends AbstractEntity<ID>, ID> extends JpaRepository<T, ID> {

  @Query("select t from #{#entityName} t where t.active = TRUE and t.id = :id")
  @NonNull Optional<T> findById(@NonNull ID id);

  @Query("select t from #{#entityName} t where t.active = TRUE")
  @NonNull List<T> findAll();

  @Override
  @Query("select e from #{#entityName} e where e.id in ?1 and e.active = TRUE")
  @NonNull List<T> findAllById(@NonNull Iterable<ID> ids);

  @Query("update #{#entityName} t set t.active = FALSE where t.id = :id")
  @Modifying
  void deleteById(@NonNull ID id);

  default void delete(T entity) {
    deleteById(entity.getId());
  }

  @Override
  default void deleteAll(Iterable<? extends T> entities) {
    entities.forEach(entity -> deleteById(entity.getId()));
  }
}

