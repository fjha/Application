package co.ga.freshpotatoes.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import co.ga.freshpotatoes.domain.entity.Film;
import co.ga.freshpotatoes.domain.entity.Genre;

public interface FilmRepositoryInterface extends PagingAndSortingRepository<Film,Long>{
	public Page findAll(Pageable pageable);

	public List<Film> findByGenre(Genre gen);
/*
	 @Query(value = "select fi from films fi where fi.genre like (select fil.genre from films fil where fil.id = ?1 )" )
	  List<Film> findGenreById(Long id , Pageable pageable);
	*/
	}
