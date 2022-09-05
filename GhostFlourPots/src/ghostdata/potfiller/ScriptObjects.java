package ghostdata.potfiller;

import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Identifiable;
import org.dreambot.api.wrappers.interactive.NPC;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ScriptObjects {

    public static ScriptObject<GameObject> HOPPER = new GameScriptObject("Hopper", 24961);
    public static ScriptObject<GameObject> HOPPER_CONTROLS = new GameScriptObject("Hopper controls", 24962);
    public static ScriptObject<GameObject> FLOUR_BIN = new GameScriptObject("Flour bin", 1781);
    public static ScriptObject<GameObject> POTTERS_WHEEL = new GameScriptObject("Potter's Wheel", 14887);
    public static ScriptObject<GameObject> POTTERY_OVEN = new GameScriptObject("Pottery Oven", 11601);
    public static ScriptObject<GameObject> WHEAT = new GameScriptObject("Wheat", 15506);
    public static ScriptObject<GameObject> CLAY_ROCK = new GameScriptObject("Rocks", 11363, 11362);

    interface ScriptObject<T extends Identifiable> {
        String getName();
        int getId();
        default Integer[] getIds() {
            return new Integer[] { getId() };
        }

        public T getClosest();
        public List<T> getAll();
        public T getRandom(int distance);
    }

    private static class GameScriptObject implements ScriptObject<GameObject> {
        final Integer[] ids;

        final String name;

        GameScriptObject(String name, int... ids) {
            this.name = name;
            this.ids = IntStream.of(ids).boxed().toArray(Integer[]::new);
        }

        @Override
        public String getName() {
            return this.name;
        }

        public int getId() {
            return this.ids[0];
        }

        @Override
        public Integer[] getIds() {
            return this.ids;
        }

        @Override
        public GameObject getClosest() {
            return GameObjects.closest(getIds());
        }

        @Override
        public List<GameObject> getAll() {
            return GameObjects.all(getIds()).stream().filter(obj -> obj != null).collect(Collectors.toList());
        }

        @Override
        public GameObject getRandom(int distance) {
            return getAll().stream().filter(obj -> obj.distance() <= distance).findAny().get();
        }
    }

    private static class NPCScriptObject implements ScriptObject<NPC> {
        final int id;
        final String name;

        NPCScriptObject(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public int getId() {
            return this.id;
        }

        @Override
        public NPC getClosest() {
            return NPCs.closest(id);
        }

        @Override
        public List<NPC> getAll() {
            return NPCs.all(id).stream().filter(npc -> npc != null).collect(Collectors.toList());
        }

        @Override
        public NPC getRandom(int distance) {
            List<NPC> all = getAll();
            return all.stream().filter(npc -> npc != null).filter(npc -> npc.distance() <= distance).findAny().get();
        }
    }
}
