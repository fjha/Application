package com.freshpotatoes.domain.repository;

import com.freshpotatoes.domain.entity.Artist;
import com.freshpotatoes.domain.entity.Film;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    List<Artist> findTop10ByFilms(List<Film> films, Sort sort);
}

