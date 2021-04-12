package org.teamlinkin.javatests.movies.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.teamlinkin.javatests.movies.model.Genre;
import org.teamlinkin.javatests.movies.model.Movie;

import java.util.Collection;

public class MovieRepositoryJdbc implements MovieRepository {
    private JdbcTemplate jdbcTemplate;

    public MovieRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Movie findById(long id) {
        Object[] args = {id};
        return jdbcTemplate.queryForObject("select * from movies where id =?",args,movieMapper);

    }

    @Override
    public Collection<Movie> findAll() {
        return jdbcTemplate.query("select * from movies",movieMapper);
        //consulta a la BD
    }

    @Override
    public void saveOrUpdate(Movie movie) {
        jdbcTemplate.update("insert into movies (name,minutes,genre) values (?,?,?)",
                movie.getName(),movie.getMinutes(),movie.getGenre().toString());
    }

    private static RowMapper<Movie> movieMapper = (resultSet, i)
            -> new Movie(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("minutes"),
                Genre.valueOf(resultSet.getString("genre")));
//Transformo cada película en un objeto movie
    /**
     * private static RowMapper<Movie> movieMapper = new RowMapper<Movie>() {
     *         @Override
     *         public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
     *             return new Movie(
     *                     resultSet.getInt("id"),
     *                     resultSet.getString("name"),
     *                     resultSet.getInt("minutes"),
     *                     Genre.valueOf(resultSet.getString("genre")));
     *         }
     *     };
     */

}
