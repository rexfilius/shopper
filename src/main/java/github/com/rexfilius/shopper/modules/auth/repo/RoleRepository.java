package github.com.rexfilius.shopper.modules.auth.repo;

import github.com.rexfilius.shopper.modules.users.model.AppRole;
import github.com.rexfilius.shopper.modules.users.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(AppRole roleName);
}
