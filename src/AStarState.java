import java.util.HashMap;
import java.util.Map;
/**
 * Этот класс хранит базовое состояние, необходимое алгоритму A* для вычисления
 * пути по карте. Это состояние включает набор «открытых путевых точек» и другой
 * набор «закрытых путевых точек». Кроме того, этот класс предоставляет основные операции,
 * необходимые алгоритму поиска пути A* для выполнения своей обработки.
 **/
public class AStarState
{
    /** Это ссылка на карту, по которой перемещается алгоритм A*. **/
    private Map2D map;
    private Map<Location, Waypoint> openWaypoint; //ключ-значение
    private Map<Location, Waypoint> closeWaypoint;


    /** Инициализировать новый объект состояния для использования алгоритмом поиска пути A*. **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
        openWaypoint = new HashMap<Location, Waypoint>(); //
        closeWaypoint = new HashMap<Location, Waypoint>();
    }

    /**  Возвращает карту, по которой перемещается навигатор A*. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * Этот метод сканирует все открытые путевые точки и возвращает путевую точку
     * с минимальной общей стоимостью. Если открытых путевых точек нет, этот
     * метод возвращает <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        Waypoint minWaypoint = null;
        Waypoint temp = null;
        float minCost = Float.MAX_VALUE;
        //Находим минимальную цену пути, проходим по открытым точкам
        for (int i = 0; i < openWaypoint.size(); i++) {
            temp = (Waypoint) openWaypoint.values().toArray()[i];
            if (temp.getTotalCost() < minCost) {
                minCost = temp.getTotalCost();
                minWaypoint = temp;
            }
        }
        return minWaypoint;
    }

    /**
     * Этот метод добавляет путевую точку (или потенциально обновляет путевую точку,
     * уже находящуюся в ней) в коллекции «открытых путевых точек».
     * Если в местоположении новой путевой точки еще нет открытой путевой точки,
     * новая путевая точка просто добавляется в коллекцию. Однако, если в местоположении
     * новой путевой точки уже есть путевая точка, новая путевая точка заменяет старую,
     * <em>только если</em> значение «предыдущая стоимость» новой путевой точки меньше,
     * чем значение «предыдущая стоимость» текущей путевой точки.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        //Поиск точки в открытых точках
        Waypoint newPoint = openWaypoint.get(newWP.getLocation());
        //Добавляем, если не найдена или заменяем, если её предыдущее значение меньше
        if (newPoint == null || newPoint.getPreviousCost() > newWP.getPreviousCost()) {
            openWaypoint.put(newWP.getLocation(),newWP);
            return true;
        }
        return false;
    }


    /** Возвращает текущее количество открытых путевых точек. **/
    public int numOpenWaypoints()
    {
        return openWaypoint.size();
    }


    /**
     * Этот метод перемещает путевую точку в указанном месте
     * из открытого списка в закрытый список.
     **/
    public void closeWaypoint(Location loc)
    {
        //если не можем пройти через точку, добавляем её в список закрытых точек
        //Получаем точку из открытых точек по хэшу локейшн
        Waypoint isPoint = openWaypoint.get(loc);
        //Если точки нет в открытых точках, то ничего не делаем
        if (isPoint == null) return;
        //Удаляем т. из открытых
        openWaypoint.remove(loc);
        //Добавляем т. в закрытые
        closeWaypoint.put(loc,isPoint);
    }

    /**
     * Возвращает true, если коллекция закрытых путевых точек содержит
     * путевую точку для указанного местоположения.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closeWaypoint.get(loc) != null;
    }
}
