package fr.kata.pandemic;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PandemicTest {




    @Test
    public void should_infect_city() {
        World world = new World("PARIS:BLUE","MADRID:BLUE","ESSEN:BLUE","MILAN:BLUE","LONDON:BLUE");
        world.link("PARIS").to("MADRID");
        world.link("PARIS").to("ESSEN");
        world.link("PARIS").to("MILAN");
        world.link("PARIS").to("LONDON");
        world.link("ESSEN").to("LONDON");
        world.link("MILAN").to("ESSEN");
        world.link("MILAN").to("LONDON");

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
        world.link("PARIS").to("MILAN");
        world.link("PARIS").to("LONDON");
        world.link("ESSEN").to("LONDON");
        world.link("MILAN").to("ESSEN");
        world.link("MILAN").to("LONDON");

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
        world.link("ST-PETERSBURG").to("ESSEN");

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
        World world = new World("PARIS:BLUE","MADRID:BLUE","ESSEN:BLUE","MILAN:BLUE","LONDON:BLUE","ALGERS:BLACK");
        world.link("PARIS").to("MADRID");
        world.link("PARIS").to("ESSEN");
        world.link("PARIS").to("MILAN");
        world.link("PARIS").to("LONDON");
        world.link("PARIS").to("ALGERS");
        world.link("ESSEN").to("LONDON");
        world.link("MILAN").to("ESSEN");
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



}
