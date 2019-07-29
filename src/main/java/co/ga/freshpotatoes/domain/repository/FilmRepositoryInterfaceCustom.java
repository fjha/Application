package co.ga.freshpotatoes.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import co.ga.freshpotatoes.domain.entity.Film;

public interface FilmRepositoryInterfaceCustom {
	public List<Film> findFilms(Long id);
}
