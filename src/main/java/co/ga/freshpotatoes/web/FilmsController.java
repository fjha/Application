package co.ga.freshpotatoes.web;

import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.HashSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import co.ga.freshpotatoes.domain.entity.Film;
import co.ga.freshpotatoes.domain.entity.Genre;
import co.ga.freshpotatoes.domain.repository.ArtistRepository;

import co.ga.freshpotatoes.domain.repository.FilmRepository;
import co.ga.freshpotatoes.domain.repository.FilmRepositoryInterface;
import co.ga.freshpotatoes.domain.repository.GenreRepository;

import co.ga.freshpotatoes.web.FilmsController;

@RestController
@Controller
public class FilmsController  {

    @Autowired GenreRepository genreRepository;
    @Autowired FilmRepository filmRepository;
    @Autowired ArtistRepository artistRepository;
   
   
  private static final String template = "id=%s, offset=%s, limit=%s\n";
 
  @RequestMapping(value="/films/{film_id}/recommendations", method=RequestMethod.GET)
 public Set<Film> recommendations(@PathVariable Long film_id) {
       return new java.util.LinkedHashSet<Film>();
        
  }
 
  
  //Based on Id display film repository
 
  @RequestMapping(value="/films/{film_id}")
  public List<Film> getFilmRecommendation(@PathVariable Long film_id ,Pageable pageable  
		  ) throws ParseException
  {
	  //This will film
	 // Film film=  filmRepository.findOne(film_id);
	  Film film=filmRepository.findOne(film_id);         
   
      //Return Genre
      Genre gen=film.getGenre();
      //Get all Genre
      List<Film>filmListByAGenre=filmRepository.findByGenre(gen);

      //Create a List for a perticular Date range only
      
      List<Film>filmListByAGenreWithinDateRange=new ArrayList<Film>();
      
      //Iterate filmListByGenre
      
      Iterator filmIterate=filmListByAGenre.iterator();
      String releaseDateYear;
      int releaseDateYearInt;
      int releaseDateYearIntAdd15;
      int releastDateYearIntSub15;
      String releaseDateRangePlus15;
      String releaseDateRangeMinus15;
      Date releaseDate,startDate,endDate;
      releaseDateYear=film.getReleaseDate().substring(0, 4);
	  releaseDateYearInt= Integer.parseInt(releaseDateYear);
      releaseDateYearIntAdd15=releaseDateYearInt+15;
	  releastDateYearIntSub15=releaseDateYearInt-15;
      releaseDateRangePlus15=Integer.toString(releaseDateYearIntAdd15)+film.getReleaseDate().substring(4);
      releaseDateRangeMinus15=Integer.toString(releastDateYearIntSub15)+film.getReleaseDate().substring(4);
      System.out.println("releaseDateRangePlus15    ------  "+releaseDateRangePlus15) ;
      System.out.println("releaseDateRangeMinus15   ------  "+releaseDateRangeMinus15);
      startDate=new SimpleDateFormat("yyyy-MM-dd").parse(releaseDateRangeMinus15);
	  endDate=new SimpleDateFormat("yyyy-MM-dd").parse(releaseDateRangePlus15);
	  
      while(filmIterate.hasNext())
      {
    	  Film eachFilm=(Film)filmIterate.next();
    	   
          releaseDate=new SimpleDateFormat("yyyy-MM-dd").parse(eachFilm.getReleaseDate());
    	 
    	  if(checkReleaseDateRange(releaseDate,startDate,endDate))
    	  {
    		  filmListByAGenreWithinDateRange.add(eachFilm);
    		  
    	  }
      }
     
     Collections.sort(filmListByAGenreWithinDateRange);
     
     return filmListByAGenreWithinDateRange;
     
     }
  @GetMapping
  @RequestMapping("/helloAll")
  String getView(Model model) {
	  model.addAttribute("msg", "Hello there, This message has been injected from the controller method");
      return "helloAllworld";
  }
  boolean checkReleaseDateRange(Date releaseDate ,Date releaseDateSub15 ,Date releaseDateAdd15)
  {
	  
	  return !(releaseDate.before(releaseDateSub15)
			  ||
			  releaseDate.after(releaseDateAdd15)
			  );
	  
  }

  
  @RequestMapping(value="/films/genre", method=RequestMethod.GET)
  public List<String> recommendationGenre()
  {
	  Iterable itr=genreRepository.findAll();
	  Iterator iterate=itr.iterator();
	  List<String> generes=new ArrayList<String>();
	  while(iterate.hasNext())
	  {
		  generes.add(iterate.next().toString());
	  }
		 return  generes;
  }
  @RequestMapping(value="/films/repository")
  public List<String> repository()
  {
	  Iterable itr=filmRepository.findAll();
	  Iterator iterate=itr.iterator();
	  List<String> generes=new ArrayList<String>();
	  while(iterate.hasNext())
	  {
		  generes.add(iterate.next().toString());
	  }
		 return  generes;
	  
  }
  @RequestMapping(value="/com/films/{film_id}")
	 public Page<Film> getFilmRecommendationTest(@PathVariable Long film_id ,Pageable pageable ) 
	  {
		// give parameter /?size=5,10,20
	     Page<Film> list=filmRepository.findAll(pageable);
	     return list ;
	  }
  
  
  @RequestMapping(value="/recommendations/{film_id}/{page}/{size}")
	 public Page<Film> getFilmRecommendationTesting(@PathVariable Long film_id ,
			 Pageable pageable,@PathVariable int page,@PathVariable int size ) throws ParseException 
	  {
	     //This will film
		 // Film film=  filmRepository.findOne(film_id);
		  Film film=filmRepository.findOne(film_id);         
	   
	      //Return Genre
	      Genre gen=film.getGenre();
	      //Get all Genre
	      List<Film>filmListByAGenre=filmRepository.findByGenre(gen);

	      //Create a List for a perticular Date range only
	      
	      List<Film>filmListByAGenreWithinDateRange=new ArrayList<Film>();
	      
	      //Iterate filmListByGenre
	      
	      Iterator filmIterate=filmListByAGenre.iterator();
	      String releaseDateYear;
	      int releaseDateYearInt;
	      int releaseDateYearIntAdd15;
	      int releastDateYearIntSub15;
	      String releaseDateRangePlus15;
	      String releaseDateRangeMinus15;
	      Date releaseDate,startDate,endDate;
	      releaseDateYear=film.getReleaseDate().substring(0, 4);
		  releaseDateYearInt= Integer.parseInt(releaseDateYear);
	      releaseDateYearIntAdd15=releaseDateYearInt+15;
		  releastDateYearIntSub15=releaseDateYearInt-15;
	      releaseDateRangePlus15=Integer.toString(releaseDateYearIntAdd15)+film.getReleaseDate().substring(4);
	      releaseDateRangeMinus15=Integer.toString(releastDateYearIntSub15)+film.getReleaseDate().substring(4);
	      System.out.println("releaseDateRangePlus15    ------  "+releaseDateRangePlus15) ;
	      System.out.println("releaseDateRangeMinus15   ------  "+releaseDateRangeMinus15);
	      startDate=new SimpleDateFormat("yyyy-MM-dd").parse(releaseDateRangeMinus15);
		  endDate=new SimpleDateFormat("yyyy-MM-dd").parse(releaseDateRangePlus15);
		  
	      while(filmIterate.hasNext())
	      {
	    	  Film eachFilm=(Film)filmIterate.next();
	    	   
	          releaseDate=new SimpleDateFormat("yyyy-MM-dd").parse(eachFilm.getReleaseDate());
	    	 
	    	  if(checkReleaseDateRange(releaseDate,startDate,endDate))
	    	  {
	    		  filmListByAGenreWithinDateRange.add(eachFilm);
	    		
	    		  
	    	  }
	      }
	    
	      Collections.sort(filmListByAGenreWithinDateRange);
		      
	      PageRequest pager = new PageRequest(page-1, size); 
	      int start = pager.getOffset();
	      int end = (start + pager.getPageSize()) > filmListByAGenreWithinDateRange .size() ? filmListByAGenreWithinDateRange .size() : (start + pager.getPageSize());        
	      int totalRows = filmListByAGenreWithinDateRange .size();
	      Page<Film> pageToReturn = new PageImpl<Film>(filmListByAGenreWithinDateRange.subList(start, end), pager, totalRows); 
	      
	      
	      if (pageable.getOffset() >= filmListByAGenreWithinDateRange.size()) {
	    	  return new PageImpl<>(filmListByAGenreWithinDateRange);
          }
	      if(size>filmListByAGenreWithinDateRange.size())
	      {
	    	  return new PageImpl<>(filmListByAGenreWithinDateRange);
	      }
	     
	      if(page*size>=filmListByAGenreWithinDateRange.size())
	     {
	    	 return new PageImpl<>(filmListByAGenreWithinDateRange);
	     }
	      else return pageToReturn;
	      
		   
		   
	     /* 
	      List<Film> userSubList = filmListByAGenreWithinDateRange.subList((pageable.getPageNumber()-1)*pageable.getPageSize(), (pageable.getPageNumber()*pageable.getPageSize())-1);
	      Page<Film> page = new PageImpl<>(userSubList, pageable,filmListByAGenreWithinDateRange.size());
	     	     
	      return page;
	      */
	  }
  
  
  
  
		 
  
}
