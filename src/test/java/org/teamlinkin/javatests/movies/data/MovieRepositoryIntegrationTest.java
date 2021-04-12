package org.teamlinkin.javatests.movies.data;

//import com.sun.tools.javac.jvm.Gen;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.teamlinkin.javatests.movies.model.Genre;
import org.teamlinkin.javatests.movies.model.Movie;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;



public class MovieRepositoryIntegrationTest {

    private MovieRepositoryJdbc movieRepository;
    private DriverManagerDataSource dataSource;

    @Before
    public void setUp() throws Exception {
        //base de datos de prueba en memoria
        dataSource =  //Me conecto a la Bd en memoria
                new DriverManagerDataSource("jdbc:h2:mem:test;MODE=MYSQL","sa","sa"); //de spring
        //ejecuto script para crear la tabla movies
        ScriptUtils.executeSqlScript(dataSource.getConnection(),new ClassPathResource("sql-scripts/test-data.sql"));

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        movieRepository = new MovieRepositoryJdbc(jdbcTemplate);
    }

    @Test
    public void load_all_movies() throws SQLException {
        Collection<Movie> movies = movieRepository.findAll();
        /**

        assertThat(movies, is(Arrays.asList(
                new Movie(1, "Dark Knight", 152, Genre.ACTION),
                new Movie(2, "Memento", 113, Genre.DRAMA),
                new Movie(3, "Matrix", 136, Genre.ACTION)
        )));
*/
        Collection<Movie> list=Arrays.asList(
                new Movie(1, "Dark Knight", 152, Genre.ACTION) ,
                new Movie(2, "Memento", 113, Genre.THRILLER) ,
                new Movie(3, "Matrix", 136, Genre.ACTION)
        );
        assertEquals(movies,list);
    }

    @Test
    public void load_movie_by_id() {
        Movie movie = movieRepository.findById(2);
        assertThat(movie, is(new Movie(2, "Memento", 113, Genre.THRILLER)));
    }

    @Test
    public void insert_a_movie() {
        Movie movie = new Movie("Super 8",112, Genre.THRILLER);
        movieRepository.saveOrUpdate(movie);
        Movie movieFromDB =movieRepository.findById(4);
        assertThat(movieFromDB, is(new Movie(4,"Super 8",112, Genre.THRILLER)));
    }

    @After
    public void tearDown() throws Exception {
        final Statement s= dataSource.getConnection().createStatement();
        s.execute("drop all objects delete files"); 
    }
}