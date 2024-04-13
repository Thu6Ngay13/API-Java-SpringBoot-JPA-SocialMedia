package SocialMedia.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Friend;
import SocialMedia.Entities.FriendId;

@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendId> {
	
	@Query("SELECT f FROM Account f")
    List<Friend> findAllFriends(String username);
}
