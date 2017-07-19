package test1.com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by T.DW on 18/7/17.
 */

@RepositoryRestResource(collectionResourceRel = "market", path = "market")
public interface MarketRepository extends PagingAndSortingRepository<Market, Long> {
}
