package fr.kata.pandemic;

import org.junit.Test;
import pandemic.World;

import static org.assertj.core.api.Assertions.assertThat;

public class PandemicTest {




    @Test
    public void should_infect_city() {
        World world = new World("PARIS:BLUE","MADRID:BLUE","ESSEN:BLUE","MILAN:BLUE","LONDON:BLUE");

        world.infect("PARIS");

        assertThat(world.infectionLevelOf("PARIS")).isEqualTo(1);
        assertThat(world.infectionLevelOf("MADRID")).isEqualTo(0);
        assertThat(world.infectionLevelOf("ESSEN")).isEqualTo(0);
        assertThat(world.infectionLevelOf("LONDON")).isEqualTo(0);
        assertThat(world.infectionLevelOf("MILAN")).isEqualTo(0);

    }


    @Test
    public void should_infect_city_three_time() {
        World world = new World("PARIS:BLUE","MADRID:BLUE","ESSEN:BLUE","MILAN:BLUE","LONDON:BLUE");
        world.link("PARIS").to("MADRID");
        world.link("PARIS").to("ESSEN");

        world.infect("PARIS");
        world.infect("PARIS");
        world.infect("PARIS");

        assertThat(world.infectionLevelOf("PARIS")).isEqualTo(3);
        assertThat(world.infectionLevelOf("MADRID")).isEqualTo(0);
        assertThat(world.infectionLevelOf("ESSEN")).isEqualTo(0);
        assertThat(world.infectionLevelOf("LONDON")).isEqualTo(0);
        assertThat(world.infectionLevelOf("MILAN")).isEqualTo(0);

    }


    @Test
    public void should_outbreak_when_city_infected_more_than_three_time() {
        World world = new World("PARIS:BLUE","MADRID:BLUE","ESSEN:BLUE","MILAN:BLUE","LONDON:BLUE","ST-PETERSBURG:BLUE");
        world.link("PARIS").to("MADRID");
        world.link("PARIS").to("ESSEN");
        world.link("PARIS").to("MILAN");
        world.link("PARIS").to("LONDON");
        world.link("ESSEN").to("LONDON");
        world.link("MILAN").to("ESSEN");
        world.link("MILAN").to("LONDON");
        world.link("ST-PETERSBURG").to("ESSEN");

        world.infect("PARIS");
        world.infect("PARIS");
        world.infect("PARIS");
        world.infect("PARIS");

        assertThat(world.infectionLevelOf("PARIS")).isEqualTo(3);
        assertThat(world.infectionLevelOf("MADRID")).isEqualTo(1);
        assertThat(world.infectionLevelOf("ESSEN")).isEqualTo(1);
        assertThat(world.infectionLevelOf("LONDON")).isEqualTo(1);
        assertThat(world.infectionLevelOf("MILAN")).isEqualTo(1);
        assertThat(world.infectionLevelOf("ST-PETERSBURG")).isEqualTo(0);

    }

    @Test
    public void should_infect_city_with_neighbour_color_when_outbreak() {
        World world = new World("PARIS:BLUE","MADRID:BLUE","ESSEN:BLUE","MILAN:BLUE","LONDON:BLUE","ALGERS:BLACK");
        world.link("PARIS").to("MADRID");
        world.link("PARIS").to("ESSEN");
        world.link("PARIS").to("MILAN");
        world.link("PARIS").to("LONDON");
        world.link("PARIS").to("ALGERS");
        world.link("ESSEN").to("LONDON");
        world.link("MILAN").to("ESSEN");
        world.link("MILAN").to("LONDON");

        world.infect("PARIS");
        world.infect("PARIS");
        world.infect("PARIS");
        world.infect("PARIS");

        assertThat(world.infectionLevelOf("PARIS","BLUE")).isEqualTo(3);
        assertThat(world.infectionLevelOf("PARIS","BLACK")).isEqualTo(0);
        assertThat(world.infectionLevelOf("ALGERS","BLACK")).isEqualTo(0);
        assertThat(world.infectionLevelOf("ALGERS","BLUE")).isEqualTo(1);

    }


    @Test
    public void should_only_outbreak_for_infection_level_of_same_color() {
        World world = new World("PARIS:BLUE","ALGERS:BLACK","ISTANBUL:BLACK");
        world.link("PARIS").to("ALGERS");
        world.link("ISTANBUL").to("ALGERS");

        world.infect("ALGERS");
        world.infect("ALGERS");
        world.infect("ALGERS");

        world.infect("PARIS");
        world.infect("PARIS");
        world.infect("PARIS");
        world.infect("PARIS");

        assertThat(world.infectionLevelOf("PARIS")).isEqualTo(3);
        assertThat(world.infectionLevelOf("ALGERS")).isEqualTo(3);
        assertThat(world.infectionLevelOf("ALGERS","BLUE")).isEqualTo(1);
        assertThat(world.infectionLevelOf("ISTANBUL")).isEqualTo(0);

        world.infect("ALGERS");

        assertThat(world.infectionLevelOf("ISTANBUL")).isEqualTo(1);

        assertThat(world.infectionLevelOf("PARIS","BLACK")).isEqualTo(1);
    }

    @Test
    public void should_only_outbreak_once_by_turn() {
        World world = new World("PARIS:BLUE","MADRID:BLUE","ESSEN:BLUE","MILAN:BLUE","LONDON:BLUE","ALGERS:BLACK","ISTANBUL:BLACK","ST-PETERSBURG:BLUE");
        world.link("PARIS").to("MADRID");
        world.link("PARIS").to("ESSEN");
        world.link("PARIS").to("MILAN");
        world.link("PARIS").to("LONDON");
        world.link("PARIS").to("ALGERS");
        world.link("ESSEN").to("LONDON");
        world.link("MILAN").to("ESSEN");
        world.link("MADRID").to("LONDON");
        world.link("MILAN").to("LONDON");
        world.link("MILAN").to("LONDON");
        world.link("ST-PETERSBURG").to("ESSEN");
        world.link("MILAN").to("ISTANBUL");

        world.infect("PARIS");
        world.infect("PARIS");
        world.infect("PARIS");

        world.infect("MADRID");
        world.infect("MADRID");
        world.infect("MADRID");

        world.infect("LONDON");
        world.infect("LONDON");

        world.infect("ESSEN");
        world.infect("ESSEN");

        world.infect("MILAN");
        world.infect("MILAN");

        world.infect("PARIS");


        assertThat(world.infectionLevelOf("PARIS")).isEqualTo(3);
        assertThat(world.infectionLevelOf("MILAN")).isEqualTo(3);
        assertThat(world.infectionLevelOf("MADRID")).isEqualTo(3);
        assertThat(world.infectionLevelOf("ESSEN")).isEqualTo(3);
        assertThat(world.infectionLevelOf("LONDON")).isEqualTo(3);
        assertThat(world.infectionLevelOf("ISTANBUL","BLUE")).isEqualTo(1);

    }


    @Test
    public void should_remove_one_level_infection_when_treat_city() {
        World world = new World("PARIS:BLUE");

        world.infect("PARIS");
        world.infect("PARIS");
        world.infect("PARIS");

        assertThat(world.infectionLevelOf("PARIS")).isEqualTo(3);
        world.treat("PARIS","BLUE");
        assertThat(world.infectionLevelOf("PARIS")).isEqualTo(2);

    }


    @Test
    public void should_loose_when_outbreak_8_times() {
        World world = new World("PARIS:BLUE");

        world.infect("PARIS");
        world.infect("PARIS");
        world.infect("PARIS");

        world.infect("PARIS");
        assertThat(world.isOver()).isFalse();
        world.infect("PARIS");
        assertThat(world.isOver()).isFalse();
        world.infect("PARIS");
        assertThat(world.isOver()).isFalse();
        world.infect("PARIS");
        assertThat(world.isOver()).isFalse();
        world.infect("PARIS");
        assertThat(world.isOver()).isFalse();
        world.infect("PARIS");
        assertThat(world.isOver()).isFalse();
        world.infect("PARIS");
        assertThat(world.isOver()).isFalse();
        world.infect("PARIS");
        assertThat(world.isOver()).isTrue();
        assertThat(world.status()).isEqualTo("TOO MANY OUTBREAKS");

    }


    @Test
    public void should_loose_when_not_enougth_disease_flag_to_infect_city() {
        World world = new World(
                "PARIS:BLUE",
                "MADRID:BLUE",
                "ESSEN:BLUE",
                "MILAN:BLUE",
                "LONDON:BLUE",
                "ST-PETERSBURG:BLUE",
                "ALGERS:BLACK",
                "ISTANBUL:BLACK",
                "MOSCOW:BLACK",
                "SAO-PAULO:YELLOW",
                "NEW-YORK:BLUE",
                "MONTREAL:BLUE",
                "WASHINGTON:BLUE",
                "ATLANTA:BLUE",
                "CHICAGO:BLUE",
                "SAN-FRANCISCO:BLUE",
                "LOS-ANGELES:YELLOW",
                "MEXICO-CITY:YELLOW",
                "MIAMI:YELLOW",
                "TOKYO:RED",
                "MANILA:RED"
                );

        world.link("PARIS").to("MILAN");
        world.link("PARIS").to("MADRID");
        world.link("PARIS").to("LONDON");
        world.link("PARIS").to("ALGERS");
        world.link("PARIS").to("ESSEN");
        world.link("ESSEN").to("LONDON");
        world.link("ESSEN").to("ST-PETERSBURG");
        world.link("ESSEN").to("MILAN");
        world.link("MILAN").to("ISTANBUL");
        world.link("ST-PETERSBURG").to("ISTANBUL");
        world.link("MADRID").to("SAO-PAULO");
        world.link("MADRID").to("LONDON");
        world.link("MADRID").to("NEW-YORK");
        world.link("LONDON").to("NEW-YORK");
        world.link("NEW-YORK").to("MONTREAL");
        world.link("NEW-YORK").to("WASHINGTON");
        world.link("MONTREAL").to("CHICAGO");
        world.link("MONTREAL").to("WASHINGTON");
        world.link("WASHINGTON").to("MIAMI");
        world.link("WASHINGTON").to("ATLANTA");
        world.link("ATLANTA").to("CHICAGO");
        world.link("ATLANTA").to("MIAMI");
        world.link("MIAMI").to("MEXICO-CITY");
        world.link("CHICAGO").to("MEXICO-CITY");
        world.link("CHICAGO").to("LOS-ANGELES");
        world.link("LOS-ANGELES").to("MEXICO-CITY");
        world.link("LOS-ANGELES").to("SAN-FRANCISCO");
        world.link("SAN-FRANCISCO").to("TOKYO");
        world.link("SAN-FRANCISCO").to("MANILA");
        world.link("SAN-FRANCISCO").to("CHICAGO");
        world.link("MADRID").to("ALGERS");

        world.infect("PARIS");
        world.infect("MADRID");
        world.infect("LONDON");
        world.infect("ST-PETERSBURG");
        world.infect("ESSEN");
        world.infect("MILAN");
        world.infect("WASHINGTON");
        world.infect("NEW-YORK");
        world.infect("ATLANTA");
        world.infect("CHICAGO");
        world.infect("MONTREAL");
        world.infect("SAN-FRANCISCO");
        // 12 blue infections
        world.infect("PARIS");
        world.infect("MADRID");
        world.infect("LONDON");
        world.infect("ST-PETERSBURG");
        world.infect("ESSEN");
        world.infect("MILAN");
        world.infect("WASHINGTON");
        world.infect("NEW-YORK");
        world.infect("ATLANTA");
        world.infect("CHICAGO");
        world.infect("MONTREAL");
        world.infect("SAN-FRANCISCO");
        // +12 = 24 blue infections

        assertThat(world.isOver()).isFalse();

        world.infect("PARIS");

        assertThat(world.isOver()).isTrue();
        assertThat(world.status()).isEqualTo("TOO MANY BLUE INFECTIONS");

    }


}
