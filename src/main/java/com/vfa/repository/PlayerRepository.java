package com.vfa.repository;

import com.vfa.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    Player findByFirstName(String firstName);

    int countByAge(int age);

    @Query("SELECT p FROM Player p WHERE p.id = ?1")
    Optional<Player> getByPlayerId(int id);

    @Query("SELECT p FROM Player p WHERE p.firstName = ?1 and p.lastName = ?2")
    Optional<Player> findByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT p FROM Player p WHERE p.team.id = ?1")
    Page<Player> getByTeamId(int id, Pageable pageable);

    @Query("SELECT p FROM Player p WHERE p.age BETWEEN ?1 AND ?2 ORDER BY p.age DESC ")
    List<Player> getByPlayerAge(int from, int to);



    //if we put PlayerResponseDTO in return type, it will never return that object
    //it will return List<Object>, That’s why we have to add new com.vfa.dto.response.PlayerResponseDTO() in query
   /* @Query("SELECT new com.vfa.dto.response.PlayerResponseDTO(p.firstName, p.lastName, p.age)" +
            " FROM Player p WHERE p.id = ?1")
    PlayerResponseDTO getPlayerInfo(int id);*/


    //Here we return the list at once, and we already regulate the implementation
    @Query("SELECT TRIM(p.firstName), p.lastName, p.age FROM Player p WHERE p.id = ?1")
    List<Object[]> getPlayerInfo1(int id);

//    void saveAllPlayers(List<Player> playerList);
}
