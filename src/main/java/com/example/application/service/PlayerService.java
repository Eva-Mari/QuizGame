package com.example.application.service;

import com.example.application.entity.Player;
import com.example.application.repository.PlayerRepository;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    public PlayerService(PlayerRepository repository){
        this.playerRepository = repository;
    }

    public List<Player> getPlayers(){

        if(playerRepository.count() == 0){
            Player player = new Player(1L, "Elsy", 10);
            Player player1 = new Player(2L, "Pippi", 5);
            playerRepository.save(player);
            playerRepository.save(player1);
        }

        return this.playerRepository.findAll();
    }

    @Valid
    public void savePlayer(Player player){
        if(player != null) {
            playerRepository.save(player);
        }
        else{
            logger.debug("Player is null");
        }
    }

    public void updateUserScore(Player player, int score){

        Player playerUpdate = playerRepository.findById(player.getId()).get();
        playerUpdate.setScore(score);
        playerRepository.save(playerUpdate);

    }

}
