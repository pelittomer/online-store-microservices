package com.online_store.user_service.api.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.online_store.user_service.api.favorite.model.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

}
