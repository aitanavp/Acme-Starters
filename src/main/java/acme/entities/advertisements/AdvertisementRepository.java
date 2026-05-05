
package acme.entities.advertisements;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.helpers.RandomHelper;
import acme.client.repositories.AbstractRepository;

@Repository
public interface AdvertisementRepository extends AbstractRepository {

	@Query("select count(a) from Advertisement a")
	int countAdvertisements();

	@Query("select a from Advertisement a")
	List<Advertisement> findAllAdvertisements(PageRequest pageRequest);

	default Advertisement findRandomAdvertisement() {
		Advertisement result;
		int count, index;
		PageRequest page;
		List<Advertisement> list;

		count = this.countAdvertisements();
		if (count == 0)
			result = null;
		else {
			index = RandomHelper.nextInt(0, count);

			page = PageRequest.of(index, 1, Sort.by(Direction.ASC, "id"));
			list = this.findAllAdvertisements(page);
			result = list.isEmpty() ? null : list.get(0);
		}

		return result;
	}

}
