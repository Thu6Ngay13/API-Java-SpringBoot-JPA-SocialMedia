package SocialMedia.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import SocialMedia.Entities.Account_SocialGroup;
import SocialMedia.Entities.Account_SocialGroup_id;

@Repository
public interface Account_SocialGroupRepository extends JpaRepository<Account_SocialGroup, Account_SocialGroup_id> {
	@Query("SELECT a FROM Account_SocialGroup a WHERE a.id.username = :username")
    List<Account_SocialGroup> findAccountSocialGroupByUsername(String username);
    
    @Query("SELECT a FROM Account_SocialGroup a WHERE a.id.groupId=:groupId")
    List<Account_SocialGroup> findAccountSocialGroupByGroupId(long groupId);
    
    @Query("SELECT a FROM Account_SocialGroup a WHERE a.id.username = :username and a.id.groupId=:groupId")
    Account_SocialGroup findOne(String username, long groupId);
    
    
}
