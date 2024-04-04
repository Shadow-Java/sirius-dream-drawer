package sirius.core.component.hub.decorator;

/**
 * @author shadow
 * @create 2024-04-04 17:03
 **/
public class SugarDecorator extends CoffeeDecorator {

    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public void makeCoffee() {
        super.makeCoffee();
        addSugar();
    }

    public void addSugar() {
        System.out.println("添加糖");
    }
}
