
import org.junit.Test;
import sirius.core.component.hub.decorator.Coffee;
import sirius.core.component.hub.decorator.MilkDecorator;
import sirius.core.component.hub.decorator.OriginalCoffee;
import sirius.core.component.hub.decorator.SugarDecorator;

/**
 * @author shadow
 * @create 2024-04-04 17:05
 **/
public class DecoratorTest {

    @Test
    public void test() {
        //原味咖啡
        Coffee coffee = new OriginalCoffee();
        coffee.makeCoffee();
        System.out.println("");

        //加奶的咖啡
        coffee = new MilkDecorator(coffee);
        coffee.makeCoffee();
        System.out.println("");

        //先加奶后加糖的咖啡
        coffee = new SugarDecorator(coffee);
        coffee.makeCoffee();
    }
}
