
package acme.features.any.advertisement;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.advertisements.Advertisement;

public interface AnyAdvertisementRepository extends AbstractRepository {

	@Query("select s from Advertisement s where s.id = :advertisementId")
	public Advertisement findAdvertisementById(int advertisementId);

	@Query("select a from Advertisement a")
	List<Advertisement> findAllAdvertisements();

}
