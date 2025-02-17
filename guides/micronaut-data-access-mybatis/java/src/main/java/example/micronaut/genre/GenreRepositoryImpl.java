package example.micronaut.genre;

import example.micronaut.ListingArguments;
import example.micronaut.domain.Genre;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Singleton // <1>
public class GenreRepositoryImpl implements GenreRepository {

    private final GenreMapper genreMapper;

    public GenreRepositoryImpl(GenreMapper genreMapper) {
        this.genreMapper = genreMapper;
    }

    @Override
    @NonNull
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(genreMapper.findById(id));
    }

    @Override
    @NonNull
    public Genre save(@NonNull @NotBlank String name) {
        Genre genre = new Genre(name);
        genreMapper.save(genre);
        return genre;
    }

    @Override
    public void deleteById(long id) {
        findById(id).ifPresent(genre -> genreMapper.deleteById(id));
    }

    @NonNull
    public List<Genre> findAll(@NonNull @NotNull ListingArguments args) {

        if (args.getMax().isPresent() && args.getSort().isPresent() && args.getOffset().isPresent() && args.getOrder().isPresent()) {
            return genreMapper.findAllByOffsetAndMaxAndSortAndOrder(
                    args.getOffset().get(),
                    args.getMax().get(),
                    args.getSort().get(),
                    args.getOrder().get());
        }

        if (args.getMax().isPresent() && args.getOffset().isPresent() && (!args.getSort().isPresent() || !args.getOrder().isPresent())) {
            return genreMapper.findAllByOffsetAndMax(args.getOffset().get(), args.getMax().get());
        }

        if ((!args.getMax().isPresent() || !args.getOffset().isPresent()) && args.getSort().isPresent() && args.getOrder().isPresent()) {
            return genreMapper.findAllBySortAndOrder(args.getSort().get(), args.getOrder().get());
        }

        return genreMapper.findAll();
    }

    @Override
    public int update(long id, @NonNull @NotBlank String name) {
        genreMapper.update(id, name);
        return -1;
    }
}
