package com.project1hour.api.core.domain.user.value;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.Getter;

/**
 * TODO : 추후 데이터베이스로 이관될 수 있음
 */
@Getter
public enum Interest {
    FOOD_TOUR("맛집"),
    COFFEE("커피"),
    DESSERT("디저트"),
    WINE_BAR("와인/바"),
    TRIP("여행"),
    DRIVE("드라이브"),
    TAKE_WALK("동네산책"),
    PET("반려동물"),
    STUDYING("공부"),
    READING("독서"),
    TENNIS("테니스"),
    RUNNING("러닝"),
    GOLF("골프"),
    HEALTH("헬스"),
    FUTSAL("풋살"),
    CYCLE("사이클"),
    YOGA("요가"),
    SPORT_CLIMBING("클라이밍"),
    WATCHING_SPORTS("스포츠관람"),
    POPUP_STORE("팝업스토어"),
    EXHIBITION("전시회"),
    CONCERT("콘서트"),
    MUSEUM("박물관"),
    MOVIE("영화"),
    MUSICAL("뮤지컬"),
    PICNIC("피크닉"),
    GAME("게임");

    public static final Map<Long, Interest> INTEREST_IDS = Arrays.stream(Interest.values())
            .collect(toMap(type -> type.id, Function.identity(), (oldValue, newValue) -> oldValue,
                    LinkedHashMap::new));

    private final long id = ordinal();

    private final String name;

    Interest(final String name) {
        this.name = name;
    }
}
