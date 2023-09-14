package com.example.application.service;

import com.example.application.entity.Player;
import com.example.application.repository.PlayerRepository;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public class PlayerService {

    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository repository){
        this.playerRepository = repository;
    }

    public List<Player> getPlayers(){
        return this.playerRepository.findAll();
    }

    @Valid
    public void savePlayer(Player player){
        playerRepository.save(player);
    }

}
