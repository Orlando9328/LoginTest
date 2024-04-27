import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.fail;

public class Login {

    //Declarando nuestra variable
    private WebDriver driver;


    //Pre condiciones
    @Before
    public void setUp(){

        //Establecemos la ruta donde se encuentra nuestro driver
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/ex/chromedriver.exe");

        //Inicializamos variable
        driver = new ChromeDriver();

        //Obtén el control de la ventana del navegador, y luego maximízala para que ocupe toda la pantalla
        driver.manage().window().maximize();

        //Abrir una URL específica en la ventana del navegador controlada por WebDriver
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    //Happy path
    //Usaremos un usuario registrado para probar que pueda entrar al sitio web con exito
    public void successfulLogIn(){
        WebElement userName = driver.findElement(By.id("user-name"));
        WebElement userPassword = driver.findElement(By.id("password"));
        WebElement button = driver.findElement(By.id("login-button"));

        //Limpiamos para que no contengan nada
        userName.clear();
        userPassword.clear();

        //Mandamos credenciales correctas para ingresar
        userName.sendKeys("standard_user");
        userPassword.sendKeys("secret_sauce");

        //Hacemos click para un ingreso exitoso
        button.click();

    }

    //No ingresaremos usuario y validaremos algunos de los comportamientos que tiene la web cuando esto sucede
    @Test
    public void unsuccessfulLogInNoUser(){

        WebElement password = driver.findElement(By.id("password"));
        WebElement button = driver.findElement(By.id("login-button"));
        password.clear();

        password.sendKeys("secret_sauce");
        button.click();

        //Validar si el campo de password name esta vacio
        WebElement user = driver.findElement(By.id("user-name"));
        String textBox = user.getText();

        if (!textBox.isEmpty()){
            fail("El campo de texto no esta vacio");
        }

        //Validar que aparece un cuadro rojo con mensaje de alerta y este sea el esperado
        WebElement redDiv = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[1]/div/div/form/div[3]/h3"));
        Assert.assertEquals("Epic sadface: Username is required", redDiv.getText());

        if (!redDiv.getText().equals("Epic sadface: Username is required")){
            System.out.println("Mensaje no es el esperado");
        }

        //Validar que la si no se agrega user name se muestre un icono X
        WebElement iconError = driver.findElement(By.className("fa-times-circle"));

        if(!iconError.isDisplayed()){
            fail("El icono no se desplegó");
        }

        //Validar si el campo en el formulario web tiene el texto "username" como su atributo "placeholder".
        String placeholder = user.getAttribute("placeholder");
        Assert.assertEquals("Username", placeholder);

    }

    @After
    public void tearDown(){
        driver.quit();

    }

}
