package it.esempio.superherobatch.util;

import it.esempio.superherobatch.model.Missione;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils {

  //  private static final String TESTO_DI_PROVA="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus et neque non turpis laoreet gravida. Vivamus molestie vestibulum elit, eu tincidunt arcu. Praesent luctus, felis vitae luctus tristique, orci eros commodo quam, et luctus velit nunc vel magna. Aenean eget mollis odio. Mauris blandit auctor vehicula. Pellentesque tristique velit non diam tempus porta. Ut finibus eros ac placerat tincidunt. Morbi sed turpis egestas, blandit lorem vel, hendrerit velit. Donec porttitor, libero eget luctus tristique, ligula felis posuere massa, ut porttitor arcu nisl sit amet ipsum. Etiam justo risus, elementum id arcu sit amet, sodales mollis leo. Fusce imperdiet mattis est et luctus. Nulla placerat pharetra eros, id iaculis ligula egestas eu. Aliquam a nisi pulvinar, varius elit ut, gravida dolor. Maecenas elementum est eget venenatis fringilla.";
  private static final String TESTO_DI_PROVA="Missione di ";
    private static final String EROI= "Astro Boy,Bananaman,Bionic Woman,Brundlefly,Chuck Norris,Ethan Hunt,Flash Gordon,Godzilla,Jason Bourne,Katniss Everdeen,King Kong,Kool-Aid Man,Rambo,The Cape,Daphne Powell,Jim Powell,JJ Powell,Stephanie Powell,Abe Sapien,Alien,Angel,Buffy,Captain Midnight,Dash,Elastigirl,Hellboy,Jack-Jack,Johann Krauss,Liz Sherman,Mr Incredible,Predator,T-1000,T-800,T-850,T-X,Violet Parr,Abin Sur,Adam Strange,Alan Scott,Amazo,Animal Man,Anti-Monitor,Aquababy,Aqualad,Aquaman,Ares,Atlas,Atlas,Atom,Atom Girl,Atom II,Atom III,Atom IV,Azrael,Aztar,Bane,Batgirl,Batgirl IV,Batgirl VI,Batman,Batman II,Batwoman V,Beast Boy,Ben 10,Big Barda,Bizarro,Black Adam,Black Canary,Black Flash,Black Lightning,Black Manta,Blue Beetle,Blue Beetle II,Blue Beetle III,Booster Gold,Brainiac,Brainiac 5,Bumblebee,Bushido,Captain Atom,Captain Cold,Captain Marvel,Captain Marvel II,Catwoman,Chameleon,Cheetah,Cheetah II,Cheetah III,Citizen Steel,Cyborg,Cyborg Superman,Darkseid,Deadman,Deadshot,Deathstroke,Doctor Fate,Donna Troy,Doomsday,Dr Manhattan,Elongated Man\n" +
            "Enchantress,Etrigan,Faora,Fighting Spirit,Firestorm,Flash,Flash II,Flash III,Flash IV,Garbage Man,General Zod,Giganta,Gog,Gorilla Grodd,Granny Goodness,Green Arrow,Guy Gardner,Hal Jordan,Harley Quinn,Hawk,Hawkgirl,Hawkman,Heat Wave,Huntress,Impulse,Indigo,Isis,Jessica Cruz,John Constantine,John Stewart,Joker,Karate Kid,Kevin 11,Kid Flash,Killer Croc,Killer Frost,Kilowog,King Shark,Krypto,Kyle Rayner,Lex Luthor,Light Lass,Lightning Lad,Lightning Lord,Lobo,Magog,Man-Bat,Martian Manhunter,Match,Maxima,Mera,Metallo,Metamorpho,Metron,Micro Lad,Misfit,Miss Martian,Mister Freeze,Mister Mxyzptlk,Monarch,Nightwing,Offspring,Oracle,Osiris,Ozymandias,Parademon,Penguin,Phantom,Phantom Girl,Plastic Man,Plastique,Poison Ivy,Power Girl,Professor Zoom,Question,Ra's Al Ghul,Raven,Ray,Red Arrow,Red Hood,Red Robin,Red Tornado,Rick Flag,Riddler,Rip Hunter,Robin,Robin II,Robin III,Robin V,Robin VI,Rorschach,Scarecrow,Shadow Lass,Silk Spectre,Silk Spectre II,Simon Baz,Sinestro,Siren,Siren II,Sobek\n" +
            "Solomon Grundy,Space Ghost,Spectre,Speedy,Starfire,Stargirl,Static,Steel,Steppenwolf,Superboy,Superboy-Prime,Supergirl,Superman,Swamp Thing,The Comedian,Trickster,Trigon,Triplicate Girl,Vibe,Vixen,Warp,White Canary,Wildfire,Wonder Girl,Wonder Woman,Zatanna,Zoom,Boba Fett,Darth Maul,Darth Vader,Greedo,Han Solo,Indiana Jones,Jar Jar Binks,K-2SO,Kylo Ren,Luke Skywalker,Rey,Stormtrooper,Yoda,Birdman,Energy,Quantum,Titan,Big Daddy,Hit-Girl,Kick-Ass,Red Mist,Donatello,Leonardo,Michelangelo,Raphael,Angela,Anti-Spawn,Billy Kincaid,Bomb Queen,Cogliostro,Curse,Cy-Gor,Man of Miracles,Overtkill,Redeemer II,Redeemer III,Savage Dragon,Spawn,Violator,Harry Potter,Sauron,3-D Man,A-Bomb,Abomination,Abraxas,Absorbing Man,Agent Bob,Agent Zero,Air-Walker,Ajax,Ammo,Angel,Angel Dust,Angel Salvadore,Annihilus,Ant-Man,Ant-Man II,Anti-Venom,Apocalypse,Arachne,Archangel,Arclight,Ardina,Ares,Ariel,Armor,Atlas,Atlas,Aurora,Azazel,Banshee,Bantam,Battlestar\n" +
            "Beak,Beast,Beta Ray Bill,Beyonder,Big Man,Binary,Bird-Brain,Bird-Man,Bird-Man II,Bishop,Black Abbott,Black Bolt,Black Cat,Black Knight III,Black Mamba,Black Panther,Black Widow,Blackout,Blackwing,Blackwulf,Blade,Bling!,Blink,Blizzard,Blizzard II,Blob,Bloodaxe,Bloodhawk,Bloodwraith,Boom-Boom,Box IV,Brother Voodoo,Bullseye,Bumbleboy,Cable,Callisto,Cannonball,Captain America,Captain Britain,Captain Marvel,Captain Planet,Captain Universe,Carnage,Cat,Century,Cerebra,Chamber,Chameleon,Changeling,Cloak,Colossus,Copycat,Cottonmouth,Crimson Crusader,Crimson Dynamo,Crystal,Cyclops,Dagger,Daredevil,Darkhawk,Darkstar,Dazzler,Deadpool,Deathlok,Demogoblin,Destroyer,Diamondback,Doc Samson,Doctor Doom,Doctor Doom II,Doctor Octopus,Doctor Strange,Domino,Doppelganger,Dormammu,Drax the Destroyer,Ego,Electro,Elektra,Emma Frost,Evil Deadpool,Evilhawk,Exodus,Fabian Cortez,Falcon,Fallen One II,Feral,Fin Fang Foom,Firebird,Firelord,Firestar,Forge,Franklin Richards,Frenzy,Frigga,Galactus,Gambit,Gamora,Genesis,Ghost Rider\n" +
            "Ghost Rider II,Gladiator,Goblin Queen,Goliath IV,Gravity,Green Goblin,Green Goblin II,Green Goblin III,Green Goblin IV,Groot,Havok,Hawkeye,Hawkeye II,Hela,Hellcat,Hellstorm,Hercules,Hobgoblin,Hollow,Hope Summers,Hulk,Human Torch,Husk,Hybrid,Hydro-Man,Hyperion,Iceman,Ink,Invisible Woman,Iron Fist,Iron Man,Iron Monger,Jack of Hearts,Jean Grey,Jennifer Kale,Jessica Jones,John Wraith,Jolt,Jubilee,Juggernaut,Junkpile,Justice,Kang,Klaw,Kraven II,Kraven the Hunter,Lady Bullseye,Lady Deathstrike,Leader,Leech,Legion,Living Brain,Living Tribunal,Lizard,Loki,Longshot,Luke Cage,Luna,Lyja,Mach-IV,Machine Man,Magneto,Magus,Man-Thing,Man-Wolf,Mandarin,Mantis,Marvel Girl,Maverick,Medusa,Meltdown,Mephisto,Mimic,Mister Fantastic,Mister Knife,Mister Sinister,Mockingbird,MODOK,Molten Man,Moon Knight,Moonstone,Morlun,Moses Magnum,Mr Immortal,Ms Marvel II,Multiple Man,Mysterio,Mystique,Namor,Namora,Namorita,Nebula,Negasonic Teenage Warhead,Nick Fury,Nightcrawler,Northstar,Nova,Odin,One-Above-All,Onslaught\n" +
            "Penance II,Phoenix,Plantman,Polaris,Professor X,Proto-Goblin,Psylocke,Punisher,Purple Man,Pyro,Quicksilver,Quill,Razor-Fist II,Red Hulk,Red Skull,Rhino,Ripcord,Rocket Raccoon,Rogue,Sabretooth,Sage,Sandman,Sasquatch,Scarlet Spider,Scarlet Spider II,Scarlet Witch,Scorpia,Scorpion,Sebastian Shaw,Sentry,Shadow King,Shadowcat,Shang-Chi,Shatterstar,She-Hulk,She-Thing,Shocker,Shriek,Sif,Silk,Silver Surfer,Silverclaw,Siryn,Skaar,Snowbird,Songbird,Speedball,Spider-Girl,Spider-Gwen,Spider-Man,Spider-Woman,Spider-Woman III,Spider-Woman IV,Spyke,Star-Lord,Stardust,Storm,Sunspot,Swarm,Synch,Taskmaster,Tempest,Thanos,Thing,Thor,Thor Girl,Thunderbird,Thunderbird III,Thunderstrike,Thundra,Tiger Shark,Tigra,Tinkerer,Toad,Toxin,Triton,Ultragirl,Ultron,Utgard-Loki,Valkyrie,Vanisher,Venom,Venom II,Venom III,Venompool,Vertigo II,Vindicator,Vision,Vulture,Walrus,War Machine,Warlock,Warpath,Wasp,Watcher,Weapon XI,Winter Soldier,Wolfsbane,Wolverine,Wonder Man\n" +
            "X-23,X-Man,Yellowjacket,Yellowjacket II,Ymir,Master Chief,Adam Monroe,Alex Woolsly,Ando Masahashi,Claire Bennet,DL Hawkins,Elle Bishop,Hiro Nakamura,Luke Campbell,Matt Parkman,Maya Herrera,Micah Sanders,Mohinder Suresh,Monica Dawson,Nathan Petrelli,Niki Sanders,Peter Petrelli,Sylar,Tracy Strauss,Judge Dredd,Goku,Naruto Uzumaki,One Punch Man,Vegeta,Hancock,Captain Hindsight,Data,James T. Kirk,Jean-Luc Picard,Kathryn Janeway,Q,Spock,Bill Harken,Cameron Hicks,Gary Bell,Nina Theroux,Rachel Pirzad,Captain Epic,Chromos,Master Brood,Omniscient,Valerie Hart,James Bond,Darkman,Alex Mercer,Allan Quatermain,Minna Murray";
    private static final List<String> LISTA_EROI = new ArrayList<>();

    static {
        LISTA_EROI.addAll(List.of(EROI.split(",")));
    }

    public static List<Missione> generatoreDiMissione(int numMission){

        List<Missione> listaMissioni= new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < numMission; i++) {
            String eroe = LISTA_EROI.get(r.nextInt(LISTA_EROI.size()));

            listaMissioni.add(Missione.builder()
                                .dettMissione(TESTO_DI_PROVA + eroe)
                                .nomeEroe(eroe)
                                .build());
        }
        return listaMissioni;

    }

    public static String getDataFormattata(){
        return getDataFormattata(LocalDateTime.now());
    }


    public static String getDataFormattata(LocalDateTime oggi){

        DateTimeFormatter dateTimeFormatter=  DateTimeFormatter.ofPattern("dd_MM_yyyy_H_m_s");
        return dateTimeFormatter.format(oggi);
    }

}
