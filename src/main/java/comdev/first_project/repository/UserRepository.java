package comdev.first_project.repository;

import comdev.first_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query(value = """
            Select 
                count(u.userName) as countUserName,
                count(u.email) as countUserEmail
            from 
                User u
            where
                u.userName = :userName
            and
                u.email = :email
                    """)
    ResultSetCustom countUserNameAndEmail(String userName, String email);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);
    boolean existsAllByEmail(String email);
}