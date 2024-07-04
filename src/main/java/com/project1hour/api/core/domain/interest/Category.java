package com.project1hour.api.core.domain.interest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Category {
    FOOD_TOUR("맛집탐방", "MEET", List.of(Interest.FOOD_TOUR)),
    CAFE_TOUR("카페투어", "MEET", List.of(Interest.COFFEE, Interest.DESSERT)),
    SIMPLE_DRINK("간술", "MEET", List.of(Interest.WINE_BAR)),
    TRIP("여행", "MEET", List.of(Interest.TRIP, Interest.DRIVE)),
    TAKE_WALK("동네산책", "MEET", List.of(Interest.TAKE_WALK)),
    PET("반려동물", "MEET", List.of(Interest.PET)),
    CAFE_STUDY("카공", "MEET", List.of(Interest.STUDYING, Interest.READING)),
    SPORTS("스포츠", "MEET",
            List.of(
                    Interest.TENNIS,
                    Interest.RUNNING,
                    Interest.GOLF,
                    Interest.HEALTH,
                    Interest.FUTSAL,
                    Interest.CYCLE,
                    Interest.YOGA,
                    Interest.SPORT_CLIMBING,
                    Interest.WATCHING_SPORTS
            )
    ),
    CULTURAL_LIFE("문화생활", "MEET",
            List.of(
                    Interest.POPUP_STORE,
                    Interest.EXHIBITION,
                    Interest.CONCERT,
                    Interest.MUSEUM,
                    Interest.MOVIE,
                    Interest.MUSICAL,
                    Interest.PICNIC
            )
    ),
    GAME("게임", "MEET", List.of(Interest.GAME)),
    SIMPLE_TALK("소소한 대화", "CHAT", Collections.EMPTY_LIST),
    HOBBY_SHARING("취미공유", "CHAT", Collections.EMPTY_LIST),
    ADVICE_TALKING("고민상담", "CHAT", Collections.EMPTY_LIST);

    private final String name;
    private final String type;
    private final List<Interest> interests;

    Category(final String name, final String type, final List<Interest> interests) {
        this.name = name;
        this.type = type;
        this.interests = interests;
    }

    public static List<String> findCategoryTypeNames(final String inputBungaeType) {
        return Arrays.stream(values())
                .filter(category -> category.type.equals(inputBungaeType))
                .map(category -> category.name)
                .toList();
    }
}
