package acme.features.administrator.advertisement;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.advertisements.Advertisement;

@Repository
public interface AdministratorAdvertisementRepository extends AbstractRepository {

	@Query("select a from Advertisement a where a.id = :advertisementId")
	Advertisement findAdvertisementById(int advertisementId);

	@Query("select a from Advertisement a")
	List<Advertisement> findAllAdvertisements();
}
