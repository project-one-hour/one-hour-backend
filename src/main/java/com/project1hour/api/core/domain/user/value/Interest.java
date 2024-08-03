package com.project1hour.api.core.domain.user.value;

import static java.util.stream.Collectors.toMap;

import com.project1hour.api.global.advice.BadRequestException;
import com.project1hour.api.global.advice.ErrorCode;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * TODO : 추후 데이터베이스로 이관될 수 있음
 */
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

    private static Map<String, Interest> INTEREST_NAMES = Arrays.stream(Interest.values())
            .collect(toMap(type -> type.name, Function.identity(), (oldValue, newValue) -> oldValue,
                    LinkedHashMap::new));

    private final String name;

    Interest(final String name) {
        this.name = name;
    }

    public static List<String> findAll() {
        return List.copyOf(INTEREST_NAMES.keySet());
    }

    public static List<Interest> findAllByNames(final List<String> interestNames) {
        return interestNames.stream()
                .map(name -> {
                    Interest interest = INTEREST_NAMES.get(name);

                    if (Objects.isNull(interest)) {
                        String message = String.format("존재하지 않는 관심사 입니다. name = %s", name);
                        throw new BadRequestException(message, ErrorCode.INCLUDE_NOT_EXISTS_INTEREST);
                    }
                    return interest;
                })
                .toList();
    }
}
