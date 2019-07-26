package co.ga.freshpotatoes.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import co.ga.freshpotatoes.domain.entity.Film;
import co.ga.freshpotatoes.domain.entity.Genre;
import co.ga.freshpotatoes.domain.repository.ArtistRepository;
import co.ga.freshpotatoes.domain.repository.FilmRepository;
import co.ga.freshpotatoes.domain.repository.GenreRepository;

@RestController
public class FilmsController {

    @Autowired GenreRepository genreRepository;
    @Autowired FilmRepository filmRepository;
    @Autowired ArtistRepository artistRepository;

  private static final String template = "id=%s, offset=%s, limit=%s\n";

  @RequestMapping(value="/films/{film_id}/recommendations", method=RequestMethod.GET)
 public Set<Film> recommendations(@PathVariable Long film_id) {
 // public int recommendations(@PathVariable Long film_id) {
      
     // int count =(int) genreRepository.count();
      return new java.util.LinkedHashSet<Film>();
    
          
  }
  //Based on Id display film repository
  @RequestMapping(value="/films/{film_id}")
  public List<Film> getFilmRecommendation(@PathVariable Long film_id) throws ParseException
  {
	  //This will film
	  Film film=  filmRepository.findOne(film_id);
                
      
	 /* 
	  String releaseDate=film.getReleaseDate();
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	  Date date;
	  Calendar calAdd15;
	  Calendar calMinus15;
	  
	try {
		date = format.parse(releaseDate);
		calAdd15 = Calendar.getInstance();
		calAdd15.setTime(date);
		calAdd15.add(Calendar.YEAR, 15);
		calMinus15 = Calendar.getInstance();
		calMinus15.setTime(date);
		calMinus15.add(Calendar.YEAR, -15);
		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
*/
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
      
      
      
      
    //  return filmListByAGenre;
     // Sort Film by its Id
      Collections.sort(filmListByAGenreWithinDateRange);
      return filmListByAGenreWithinDateRange;
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
  
  
}
