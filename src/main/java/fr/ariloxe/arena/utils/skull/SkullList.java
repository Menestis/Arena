package fr.ariloxe.arena.utils.skull;

import org.bukkit.inventory.ItemStack;

import java.util.Random;


public enum SkullList {

  ARILOXE("Ariloxe", "49cbd509170f5295b4794ec022aab2c63ccf5512669efdb6ed0aeedde89c5475"),
  DOMPE("Dompe", "313575c993800e876bc66e1758665291b3495c0a921115f6908b4211dec50e77"),
  BOBOCLEMENTO("Boboclemento", "f0d661a6b0e2e181cd8867b1453273ab163e26bcfcbfd5ba1eb9555d44ac70ee"),
  FRIXIO("Frixio", "2536d0d15a8f6d5a7e01c8ef499867f6b116688e7459156247c9ab8a309bf36a"),
  MARCO("Marco", "1ce994c5827bf265669185b44facbb7b9e332ca53328dac0da6a05e57b458959"),
  ANTO42("Anto", ""),
  EVERLASTING("Ever", "774f461261e930a1446ade8c6320ef201ce709cc07caaff41acdfc719eec6864"),
  XNIKOVAGUELETTE("xNiko", "9aacbb2e0ac54d5312bd008ab334f53e902d8738997726a1bfa5df73b8a766"),

  BLENDMAN("Blendmanisateur", "629abd9164303b2ce206f0df4b0258690d9abb4e36ba968fb5acfec5c0fd7c50"),



  ORANE18("Orane", "d7b36f6e9dead64d4573bf8e164c857d17e1de23aba137eab865fa3cb5f9043e"),
  ITSINO("ItsIno", "7385132175b31fc47cb0d512260dfab111bc309a461c17638929952efd6970d0"),
  KORSO("Korso", "fc4551edc877e0e58d1b7bb092a0e7c1101ecf1a7c8f77ac4485d39c4de6d1ce"),

  TANJIRO("Tanjiro", "79b44544f6a1f8f662d79bd53c8e52af0f4ec6d30421f053459ec2bd03606825"),
  ZENITSU("Zenitsu", "e5ede8b586a425634ebea8cb818215c2e67b53528ddb494f0431aa4bbee0ffee"),
  NEZUKO("Nezuko", "1b2dcdf6dbb40499abe5fa59252da18ff06e693e78f7841809768e7789332a51"),

  NARUTO("Naruto", "4aa4598eccb5ae88b4764eb4ecf0989faa2e14a75641e03474a25034ea39a2d0"),
  GOJO("Gojo", "be9648cb5434622ce906a8d0a26ee95e217267d3b5628e5b1d0096c360447d79"),
  ITADORI("Itadori", "4909c25469669e39040724f46d36a7b90caf57990d988aa564fdb2e4a5f62c90"),

  WOLF("Wolf", "dc3dd984bb659849bd52994046964c22725f717e986b12d548fd169367d494"),
  BEDROCK("Bedrock", "36d1fabdf3e342671bd9f95f687fe263f439ddc2f1c9ea8ff15b13f1e7e48b9"),
  LANTERNE("Lanterne", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjYyODNlN2E4"),

  CUIRASSE("Titan Cuirassé", "885386293142d9660258419fad6811d9ec26232d32229b0519605229b0851204"),
  GOLDENAPPLE("Golden Apple", "8d68a32e3298fcd4eaee89088aaa1619ccf11cc2c4d5bbb66fce654733d1dd6"),
  MUZAN("Muzan", "9551f03f6fe680fa93512223568fab0dfb9cdaa7b0c55c2282d0d52588d8c119"),
  POKEBALL("", "5fe47640843744cd5796979d1196fb938317ec42b09fccb2c545ee4c925ac2bd"),
  PLANETTE_TERRE("", "5a9d914a12c17cccb55899285a066902ba53976807407fcb8696dbe19aef77"),
  CAMERA("", "3db83586542934f8c3231a5284f2489b87678478454fca69359447569f157d14"),
  CIBLE("", "cc6b2518ae7b60e441f8fe624994e188685d6613f28ab59ab36d1f9e9c9eee36"),
  EYE("", "45556083c9cde19f54bf78a421cb9731f60f1d3de3cf584f54b2d43677df2a7"),
  CRATE_WOOD("", "f620519b74536c1f85b7c7e5e11ce5c059c2ff759cb8df254fc7f9ce781d29"),
  CRATE_IRON("", "1682de72bf61c6d23364e2fe2d7cc28ddf83145d18f193857d369cf9df692"),
  CRATE_BLUE("", "43933a6d24c8b4679912e4ff19efa56a8c970d664f6e6e4f6a2d067d5b8b"),
  CRATE_GOLD("", "cdf81449131dcdd3578899fcd9592e13f5cca57ae7481fd6710bb6ca85d65c9"),

  SNAKE("", "51b4b20189d902bc6c3fabe23d9f578697f544cf415317787374118393da4"),
  LUCKYBLOCK("", "cdd1e6bd215afa5e673285afacb85eb8d0f79a5b46c5432d6feed66097c51248"),
  MASCOTTE_COMPUTER("Computer", "e78e6a83ffa3a169fc7a12c6f6ac931ff1b212c360a56774e4c8d4e269b94e5b"),
  GIFT("Gift", "473ba546a62b32c515376dd7f3c92fb35a33524f323bbb2b5978e38c7e5f62a"),

  BLOCK_COMMANDBLOCK_DEFAULT("", "27712ca655128701ea3e5f28ddd69e6a8e63adf28052c51b2fd5adb538e1"),
  BLOCK_COMMANDBLOCK_GREEN("", "285cd8b374fa99e6acc677fcc8d9f4db90e3c337436aea26afe6e13acbdf9"),

  MARVEL_HULK("", "694c7bc1a2deaa9ac15fa78c90fc1d8114a799d2c82e62eb2050b419de963ae1"),
  MARVEL_THANOS("", "eb9e6815d741fa1f87389027c357abaae9eeada173df89bbac96dfc8b9d6a3c9"),
  MARVEL_IRON_MAN("", "b52ca97834e01458691c905445786d1262d843663d6128f3bc0c5319b8a69e4"),
  MARVEL_DEADPOOL("", "1efc4ff767cc9cb06b732912f6d670e11869356dc653a9bfd98da85fb567477"),
  MARVEL_WOLVERINE("", "bc981e9ff1aa5e2549d78815eabd8bd555e2e2591bbccbd05f4656b3382f6e"),
  MARVEL_VENOM("", "797488e721686eeca8203dc35fbbca66abebcfb2c8313cc4a7b6d7051fbd6e3"),
  MARVEL_ANTMAN("", "addc4d15e5e434e64444fdf3e6d381cbc8244ddc96f4f2eec4e2ef33be8f"),
  MARVEL_ULTRON("", "3ed6fd87efc6ec3eb83603892cb6aeb33c1621436ec2d532569848c53112ed"),
  MARVEL_GROOT("", "96e85e32b75e972e10b256d54964e19f1a8b394095ad9f331b888409de2"),
  MARVEL_CAPTAIN_AMERICA("", "c56ba7516a615683993cfd471f1876703a50d3876cc1eb8795463131ee43bceb"),
  MARVEL_SPIDERMAN("", "61b22ed3b197b484d471552f398476d5be770af98f5c93f9a0de88e56755d75c"),

  STARWARS_VADOR("", "6324e6eb8dd1d12ed81aea390380d7e566b1a2860fbefba859883d515c5eb218"),
  STARWARS_ROBOT_BB_8_BODY("", "b787dc44172d6887476aa197e0fd35d67dc37c379fbd22e646f1af173ad06a64"),
  STARWARS_ROBTO_BB_8_HEAD("", "42d271f82674916dcb47b372a572f61e7472fe063b2c9246aeeb7c57a8835"),
  STARWARS_YODA("", "a81ed9f68aa8a105dd53f4d5889e5866215a5de9a9f6441f852162949752ff"),
  STARWARS_ROBOT_R2D2("", "97a0e5bfe32f1619c6891594fb9c3b274c38e47cd01b7aafc5c4eebe3f2982a4"),
  STARWARS_ROBOT_R3Y2("", "91cf90f7b9711b58fc2136cbd0a6f7b056e3bdf4bdda5f9a286abc37d4f5384"),
  STARWARS_ROBOT_R2C4("", "59672a093f8dd17d8c6ad78e53e3a1bbb94b6375b7b912e3d04666913d5ff3"),
  STARWARS_ROBOT_R2KT("", "10a01dca4a5587fd44f099c1485e1eade5f255da144ade2e4509cb6472677"),
  STARWARS_STORMTROOPER("", "bbc1354ea76adfb93c60ccecbca85935c28828e89688c10681ac8a26862fb8"),
  STARWARS_OLD_LUKE_SKYWALKER("", "23ae9160e8f0647783cb6fbca76d3fbeb693927cd56e5acda99bc5cd8451d1d"),
  STARWARS_JAWA("", "c71459844548a40f8e1f517f2303ec91750583d66c8b3d293b1fe7bb4459a3"),
  STARWARS_GEONOSIAN("", "eefb8f061bc36ed8f29a82e9ed040b5ac2eb2c3dbb499dab2b02e4a5e6f8fd9"),
  STARWARS_CHEWBACCA("", "4335115b23a1cf4daff69b54b27132f10fa5f348bfd62b0ef3d8c51ab894b"),

  YOUTUBE("", "b4353fd0f86314353876586075b9bdf0c484aab0331b872df11bd564fcb029ed"),
  DISCORD("", "b2efa83c998233e9deaf7975ace4cd16b6362a859d5682c36314d1e60af"),
  FACEBOOK("", "deb46126904463f07ecfc972aaa37373a22359b5ba271821b689cd5367f75762"),
  TWITTER("", "3685a0be743e9067de95cd8c6d1ba21ab21d37371b3d597211bb75e43279"),
  
  RED_BALL("", "533a5bfc8a2a3a152d646a5bea694a425ab79db694b214f156c37c7183aa"),
  ORANGE_BALL("", "adc56355f11ce53e14d374ecf2a0b255301b734d99c674240afacc73e2145c"),
  YELLOW_BALL("", "41139b3ef2e4c44a4c983f114cbe948d8ab5d4f879a5c665bb820e7386ac2f"),
  LIME_BALL("", "c607cffcd7864ff27c78b29a2c5955131a677bef6371f88988d3dcc37ef8873"),
  GREEN_BALL("", "85484f4b6367b95bb16288398f1c8dd6c61de988f3a8356d4c3ae73ea38a42"),
  CYAN_BALL("", "cb9b2a4d59781d1bec2d8278f23985e749c881b72d7876c979e71fda5bd3c"),
  TURQUOISE_BALL("", "4f26ea93d5fd19a3808a5e5885fc29612657d83dfab9bff527279ccbd6f16"),
  BLUE_BALL("", "d1137b9bf435c4b6b88faeaf2e41d8fd04e1d9663d6f63ed3c68cc16fc724"),
  PURPLE_BALL("", "a1b9a0a6d1b9912794289eca1e224eae6d76a7cb752ca689f1b991ce970adee"),
  MAGENTA_BALL("", "3dd0115e7d84b11b67a4c6176521a0e79d8ba7628587ae38159e828852bfd"),
  PINK_BALL("", "c54deeeab3b9750c77642ec95e37fe2bdf9adc555e0826dedd769bedd10"),
  BROWN_BALL("", "eaa628e0ed229a4d782c319debdb64af9ef910d52bf9e91458544ccfd1602d"),
  WHITE_BALL("", "caf039bec1fc1fb75196092b26e631f37a87dff143fc18297798d47c5eaaf"),
  LIGHTGRAY_BALL("", "145fd2c3736af4bd2811296661e0cb49bab2cfa65f5c9e598aa43bebfa1ea368"),
  GRAY_BALL("", "da3d7a709717d1ffe2402448d867b1a7f32fb8955621cc8977b61f9a3cbc95"),
  BLACK_BALL("", "3e1e5c81fb9d64b74996fd171489deadbb8cb772d52cf8b566e3bc102301044"),

  ENDERDRAGON_BALL("", "87cf21ccb21e2d29c81cc15fe8d3b3ef971d182d3224a212964dddb36cf4");
  
  private String name;
  private String texture;
  
  SkullList(String name, String texture) {
    this.name = name;
    this.texture = texture;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getTextureLink() {
    return "http://textures.minecraft.net/texture/" + getTextureHash();
  }
  
  public String getTextureHash() {
    return this.texture;
  }
  
  public ItemStack getItemStack() {
    return SkullUtils.getSkullByURL(getTextureLink());
  }

  public ItemStack getItemStack(String displayname) {
    return SkullUtils.getSkullByURL(getTextureLink(), displayname);
  }

  public ItemStack getItemStack(String displayname, String... lores) {
    return SkullUtils.getSkullByURL(getTextureLink(), displayname, lores);
  }

  public ItemStack getItemStack(String displayname, String lore) {
    return SkullUtils.getSkullByURL(getTextureLink(), displayname, lore);
  }

  public ItemStack getItemStackWithName() {
    return SkullUtils.getSkullByURL(getTextureLink(), getName());
  }

  public static ItemStack getRandomSkull() {
    int r = (new Random()).nextInt((values()).length);
    return SkullUtils.getSkullByURL(values()[r].getTextureLink());
  }

  public static ItemStack getRandomSkull(String displayname) {
    int r = (new Random()).nextInt((values()).length);
    return SkullUtils.getSkullByURL(values()[r].getTextureLink(), displayname);
  }

  public static ItemStack getRandomSkull(String displayname, String... lores) {
    int r = (new Random()).nextInt((values()).length);
    return SkullUtils.getSkullByURL(values()[r].getTextureLink(), displayname, lores);
  }
  
  public static ItemStack getRandomSkull(String displayname, String lore) {
    int r = (new Random()).nextInt((values()).length);
    return SkullUtils.getSkullByURL(values()[r].getTextureLink(), displayname, lore);
  }
}
