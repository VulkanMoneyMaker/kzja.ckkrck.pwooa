package com.slotclubparty.tophard.business.main;


import java.util.List;

import io.reactivex.Single;

public interface IMainIteractor {
    Single<List<Movie>> getPopularMovie(int page);
    Single<List<Movie>> getTopRated(int page);
}
