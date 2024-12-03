package ge.tcb.testacademy;

//I'm trying to do this project in really short time (it's my fault) it's not flawless but do some things
//ყველა კონსტანტა ვერ გადმოვიტანე ცოტა დროის მენეჯმენტში მიჭირს
//ასევე არა რუსულ "მთავრობას"
public class Constants {

    public static final String KINO="https://swoop.ge/movies/";
    public static final String KARTINGI = "https://www.swoop.ge/category/2058/sporti/kartingi/";
    public static final String KARTINGI_WITHOUT_WWW = "https://swoop.ge/category/2058/sporti/kartingi/";
    public static final String SWOOP = "https://swoop.ge/";
    public static final String SWOOP_DASVENEBA = "https://swoop.ge/category/24/dasveneba/";
    //  public static final int NUMBER_OF_OFFERS = 255;
    public static final String KLEBADI_XPATH = "//p[text()='ფასით კლებადი']";
    public static final String ZRDADI_XPATH = "//p[text()='ფასით ზრდადი']";
    public static final String SHESABAMISOBIT_XPATH = "//button//p[text()='შესაბამისობით']";

    public static final String OFFERS_XPATH = "a.group.flex.flex-col.gap-3.cursor-pointer";


  //  public static final String RIGHT_ARROW = "//div[@class='flex h-9 w-9 items-center justify-center rounded-lg cursor-pointer hover:bg-secondary_gray-10-value transition-colors duration-300']//img[@src='/icons/ep_arrow-right-bold.svg']";

    public static final String LOGO = "//a[@href='/' and contains(@class, 'cursor-pointer')]/img[@alt='swoop']";

}
