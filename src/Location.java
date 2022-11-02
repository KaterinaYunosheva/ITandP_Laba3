/**
 * This class represents a specific location in a 2D map.  Coordinates are
 * integer values. Этот класс представляет координаты конкретной
 * ячейки на карте
 **/
public class Location
{
    /** X coordinate of this location. **/
    public int xCoord;

    /** Y coordinate of this location. **/
    public int yCoord;


    /** Создает новое местоположение с указанными целочисленными координатами. **/
    public Location(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /** Создает новую локацию с координатами (0, 0). **/
    public Location()
    {
        this(0, 0);
    }

    public boolean equals(Object object){
        //Проверяем является ли объект экземпляром класса Location (получаем ссылку на объект)
        if (object instanceof Location){
            Location loc = (Location) object;
            //Если является, сравниваем его координаты
            return (loc.xCoord == xCoord && loc.yCoord == yCoord);
        }
        //Иначе возвращаем false
        return false;
    }

    public int hashCode(){
        //Возвращаем хэш-код объекта по его координатам.
        return (xCoord * 9)*(xCoord - 1) + (yCoord * (xCoord - 4));
    }
}