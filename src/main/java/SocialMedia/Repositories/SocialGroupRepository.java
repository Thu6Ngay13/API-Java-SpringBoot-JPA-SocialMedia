package SocialMedia.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.SocialGroup;

@Repository
public interface SocialGroupRepository  extends JpaRepository<SocialGroup, Long>{
	Optional<SocialGroup> findByGroupId(long groupId);
	List<SocialGroup> findByGroupNameContainingIgnoreCase(String searchString);
	
	@Query("SELECT s FROM SocialGroup s "
			+ "JOIN Account_SocialGroup asg ON s.groupId = asg.id.groupId "
			+ "WHERE asg.id.username = :username "
				+ "AND asg.isAccepted = true "
				+ "AND s.groupName LIKE %:keyword%")
	List<SocialGroup> searchJoinedGroup(@Param("username") String username, @Param("keyword") String keyword);
	
	@Query("SELECT s FROM SocialGroup s "
			+ "WHERE s.groupId NOT IN "
				+ "(SELECT DISTINCT asg.id.groupId FROM Account_SocialGroup asg "
            	+ "WHERE asg.id.username LIKE :username) "
        	+ "AND s.groupName LIKE %:keyword%")	
	List<SocialGroup> searchJoinGroup(@Param("username") String username, @Param("keyword") String keyword);

	@Query("SELECT DISTINCT s FROM SocialGroup s "
			+ "JOIN Account_SocialGroup asg ON s.groupId = asg.id.groupId "
			+ "WHERE asg.isAccepted = false "
				+ "AND asg.id.username = :username "
				+ "AND s.groupName LIKE %:keyword% ")
	List<SocialGroup> searchUnjoinGroup(@Param("username") String username, @Param("keyword") String keyword);

}
