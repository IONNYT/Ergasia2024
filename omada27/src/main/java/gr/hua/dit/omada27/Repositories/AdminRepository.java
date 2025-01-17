package gr.hua.dit.omada27.Repositories;

import gr.hua.dit.omada27.Entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {


    Admin findByUsername(String username);

    Admin findByEmail(String email);
}
