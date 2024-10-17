package com.project1hour.api.core.domain.bungae.entity;

import com.project1hour.api.core.domain.user.entity.Interest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Getter;

@Getter
public enum Category {
    FOOD_TOUR("맛집탐방", "MEET",
            List.of(
                    Interest.FOOD_TOUR.getId()
            )
    ),
    CAFE_TOUR("카페투어", "MEET",
            List.of(
                    Interest.COFFEE.getId(),
                    Interest.DESSERT.getId()
            )
    ),
    SIMPLE_DRINK("간술", "MEET",
            List.of(
                    Interest.WINE_BAR.getId()
            )
    ),
    TRIP("여행", "MEET",
            List.of(
                    Interest.TRIP.getId(),
                    Interest.DRIVE.getId()
            )
    ),
    TAKE_WALK("동네산책", "MEET",
            List.of(
                    Interest.TAKE_WALK.getId()
            )
    ),
    PET("반려동물", "MEET",
            List.of(Interest.PET.getId()
            )
    ),
    CAFE_STUDY("카공", "MEET",
            List.of(
                    Interest.STUDYING.getId(),
                    Interest.READING.getId()
            )
    ),
    SPORTS("스포츠", "MEET",
            List.of(
                    Interest.TENNIS.getId(),
                    Interest.RUNNING.getId(),
                    Interest.GOLF.getId(),
                    Interest.HEALTH.getId(),
                    Interest.FUTSAL.getId(),
                    Interest.CYCLE.getId(),
                    Interest.YOGA.getId(),
                    Interest.SPORT_CLIMBING.getId(),
                    Interest.WATCHING_SPORTS.getId()
            )
    ),
    CULTURAL_LIFE("문화생활", "MEET",
            List.of(
                    Interest.POPUP_STORE.getId(),
                    Interest.EXHIBITION.getId(),
                    Interest.CONCERT.getId(),
                    Interest.MUSEUM.getId(),
                    Interest.MOVIE.getId(),
                    Interest.MUSICAL.getId(),
                    Interest.PICNIC.getId()
            )
    ),
    GAME("게임", "MEET",
            List.of(
                    Interest.GAME.getId()
            )
    ),

    SIMPLE_TALK("소소한 대화", "CHAT"),
    HOBBY_SHARING("취미공유", "CHAT"),
    ADVICE_TALKING("고민상담", "CHAT");

    private static final long OFFSET = 1;

    private final long id;
    private final String name;
    private final String type;
    private final List<Long> interestIds;

    Category(final String name, final String type, final List<Long> interestIds) {
        this.id = this.ordinal() + OFFSET;
        this.name = name;
        this.type = type;
        this.interestIds = interestIds;
    }

    Category(final String name, final String type) {
        this(name, type, Collections.emptyList());
    }

    public static List<String> findCategoryTypeNames(final String inputBungaeType) {
        return Arrays.stream(values())
                .filter(category -> category.type.equals(inputBungaeType))
                .map(category -> category.name)
                .toList();
    }
}
