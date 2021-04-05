package graphics;


import entity.AbstractEntity;
import entity.EntityState;
import entity.EntityType;
import entity.Player;
import graphics.elements.Move;
import graphics.map.Theme;
import graphics.window.GameWindow;
import items.AbstractItem;
import items.collectables.ConsumableTypes;
import items.collectables.EquipmentTypes;

public enum Language {
    EN, FR, IT, AR;

    private static String lang(String en, String fr, String it, String ar) {
        Language l = GameWindow.language();
        return l == IT ? it : (l == FR ? fr : (l == EN ? en : ar));
    }


    // ===== Start menu buttons ===== //
    public static String newGame() {
        return lang("New Game", "Nouvelle Partie", "Nuova Partita", "جزء جديد");
    }
    public static String options() {
        return lang("Options", "Options", "Opzioni", "خيارات");
    }
    public static String exitGame() {
        return lang("Exit Game", "Quitter le Jeu", "Esci dal Gioco", "اترك اللعبة");
    }
    public static String back() {
        return lang("Back", "Retour", "Indietro", "يعود");
    }
    public static String startTheQuest() {
        return lang("Start the Quest", "Commencer la Quête", "Inizia la Missione", "ابدأ المهمة");
    }
    public static String validate() {
        return lang("Validate", "Valider", "Applica", "تحقق");
    }



    // ===== Chara selection menu ===== //
    public static String chooseYourSpeciality() {
        return lang("Choose your speciality", "Choisissez votre spécialité", "Scegli la tua specialità", "اختر تخصصك");
    }
    public static String warriorCL() {
        return lang("WARRIOR", "GUERRIER", "GUERRIERO", "محارب"); /* CL is for 'CapsLock'  */
    }
    public static String archerCL() {
        return lang("ARCHER", "ARCHER", "ARCIERE", "آرتشر"); /* CL is for 'CapsLock'  */
    }
    public static String mageCL() {
        return lang("MAGE", "MAGE", "MAGO", "بركه"); /* CL is for 'CapsLock'  */
    }
    public static String warriorDescription() {
        String body1 = lang("The warrior deals great melee damage and has a large amount of HP.",
                "Le guerrier inflige de puissants dommages de mêlée et possède une large quantité de PV.",
                "Il guerriero infligge grandi danni di mischia e possiede una grossa quantità di HP.",
                "يلحق المحارب ضررًا قويًا بالاشتباك ويتمتع بقدر كبير من الصحة.");
        String body2 = lang("In counterpart, he loses 1 Hunger Point for each attack.",
                "En contrepartie, il perd 1 Point de Faim à chaque attaque.",
                "Tuttavia, perde un Punto Fame a ogni attacco.",
                "في المقابل ، يخسر نقطة جوع واحدة لكل هجوم.");
        return "<html><p style=\"text-align: center;\">" + body1 + "<br />" + body2 + "</p></html>";
    }
    public static String archerDescription() {
        String body1 = lang("The archer deals good distance damage with his very long range but has few HP.",
                "L'archère inflige de bons dommages à distance avec une grande portée mais possède peu de PV.",
                "L'arciere infligge danni a distanza grazia alla sua grande portata, ma possiede pochi HP.",
                "يتعامل رامي السهام مع ضرر جيد بعيد المدى ولكنه يتمتع بصحة قليلة.");
        String body2 = lang("Each attack has a chance to deal more damage, inflict an effect... or miss the target.",
                "Chaque attaque a une chance d'infliger plus de dégâts, d'apposer un effet... ou de rater la cible.",
                "Ogni attacco ha la probabilità di essere più forte, di infliggere un effetto... o di mancare il bersaglio.",
                "كل هجوم لديه فرصة لإحداث المزيد من الضرر ، أو إحداث تأثير ... أو تفويت الهدف.");
        return "<html><p style=\"text-align: center;\">" + body1 + "<br />" + body2 + "</p></html>";
    }
    public static String mageDescription() {
        String body1 = lang("The mage deals moderate damage in a medium range.",
                "Le mage inflige des dégâts modérés dans une portée moyenne.",
                "Il mago infligge dei danni moderati con un raggio d'attacco medio.",
                "يتسبب السحرة في ضرر معتدل في مدى متوسط.");
        String body2 = lang("His power lies in his ability to burn his opponents and heal himself slightly with each attack.",
                "Sa force réside dans sa capactié à brûler ses adversaires et se soigner légèrement à chaque attaque.",
                "La sua forza è la capacità di bruciare i suoi avversari e curarsi leggermente a ogni attacco.",
                "تكمن قوته في قدرته على حرق خصومه وشفاء نفسه قليلاً مع كل هجوم.");
        return "<html><p style=\"text-align: center;\">" + body1 + "<br />" + body2 + "</p></html>";
    }



    // ===== Options menu ===== //
    public static String selectTheLanguage() {
        return lang("Select language", "Sélectionnez la langue", "Seleziona la lingua", "اختار اللغة");
    }
    public static String gameSound() {
        return lang("Game sound", "Son du jeu", "Suono del gioco", "صوت اللعبة");
    }
    public static String chooseTheDifficulty() {
        return lang("Choose the difficulty", "Choisissez la difficulté", "Scegli la difficoltà", "...");
    }
    public static String easy() {
        return lang("Easy", "Facile", "Facile", "...");
    }
    public static String medium() {
        return lang("Medium", "Moyen", "Medio", "...");
    }
    public static String hard() {
        return lang("Hard", "Difficile", "Difficile", "...");
    }
    public static String nightmare() {
        return lang("Nightmare", "Cauchemar", "Incubo", "...");
    }
    public static String endless() {
        return lang("Endless", "Sans fin", "Infinito", "...");
    }



    // ===== Stats panel (in game) ===== //
    public static String heroCP() {
        return lang("HERO", "HÉROS", "EROE","بطل");
    }
    public static String level() {
        return lang("Level", "Niveau ", "Livello","مستوى");
    }
    public static String hp() {
        return lang("HP", "PV", "HP","الحياة");
    }
    public static String hunger() {
        return lang("Hunger", "Faim ", "Fame","جوع");
    }
    public static String attack() {
        return lang("Attack", "Attaque ", "Attacco", "هجوم");
    }
    public static String defense() { return lang("Defense", "Défense", "Difesa", "دفاع");}
    public static String range() {
        return lang("Range", "Portée ", "Portata", "مدى");
    }
    public static String pressIForInventory(boolean b) {
        return lang("Press [i] to " + (b? "open":"close") + " INVENTORY.",
                "Appuyez sur [i] pour " + (b? "ouvrir":"fermer") + " l'INVENTAIRE.",
                "Premere [i] per " + (b? "aprire":"chiudere") + " l'INVENTARIO.",
                "اضغط [i] إلى " + (b? "لفتح":"اغلاق") + " المخزون.");
    }
    public static String money() {
        return lang("Money","Monnaie ", "Moneta","فضة");
    }
    public static String stage() {
        return lang("Stage", "Étage ", "Piano", "...");
    }



    // ===== Logs ===== //
    public static String logGainMoney(int value) {
        return lang("You have found: " + value + " coin" + (value>1? "s!": "!"),
                "Vous avez trouvé : " + value + " pièce" + (value>1? "s !": " !"),
                "Hai trovato: " + value + " monet" + (value>1? "e!": "a!"),
                "انت وجدت : " + value  + (value>1? "عملات معدنية" : "عملة "));
    }
    public static String logGainItem(AbstractItem i) {
        return lang("You have found: " + i + "!",
                "Vous avez trouvé : " + i + " !",
                "Hai trovato: " + i + "!",
                "انت وجدت : " + i + " !");
    }
    public static String logLowerFloor() {
        return lang("You enter the lower floor...",
                "Vous pénétrez à l'étage inférieur...",
                "Si entra nel piano inferiore...",
                "تدخل الطابق السفلي ...");
    }
    public static String logNothingHappens() {
        return lang("Immune.", "Immunisé.", "Immune.", "مناعة.");
    }
    public static String logDealDamage(AbstractEntity entity1, AbstractEntity entity2) {
        boolean b = entity2 instanceof Player;
        int dmg = (Math.max(0, entity1.getAttack() - (b ? Player.getInstancePlayer().getDefense() : 0)));
        return lang(entity1 + " deals " + dmg + " damage to " + entity2 + "." + (dmg != entity1.getAttack() ? "[-" + (entity1.getAttack() - dmg) + " Attack]" : ""),
                entity1 + " inflige " + dmg + " dégâts à " + entity2 + "." + (dmg != entity1.getAttack() ? "[-" + (entity1.getAttack() - dmg) + " Attaque]" : ""),
                entity1 + " infligge " + dmg + " danni a " + entity2 + "." + (dmg != entity1.getAttack() ? "[-" + (entity1.getAttack() - dmg) + " Attacco]" : ""),
                entity1 + " يلحق " + dmg + " ضرر لأ " + entity2 + "." + (dmg != entity1.getAttack() ? " هجوم]" + (entity1.getAttack() - dmg) + "-]" : ""));
    }
    public static String logCriticalHit(AbstractEntity entity1, AbstractEntity entity2, int atk) {
        return lang(entity1 + " deals " + atk + " damage to " + entity2 + " (critical hit)!",
                entity1 + " inflige " + atk + " de dégât à " + entity2 + " (coup critique) !",
                entity1 + " infligge " + atk + " danni a " + entity2 + "(colpo critico)!",
                entity1 + " يلحق " + atk + " ضرر لأ " + entity2 + "((ضربة حاسمة) !");
    }
    public static String logMissedTarget() {
        return lang("Missed target...", "Cible manquée...", "Bersaglio mancato...","هدف مفقود ...");
    }
    public static String logModifyHunger(int x) {
        return lang("You " + (x >= 0 ? "gain " + x : "lose " + (-x)) + " Hunger Point" + (x > 1 || x < -1 ? "s" : "") + ".",
                "Vous " + (x >= 0 ? "gagnez " + x : "perdez " + (-x)) + " Point" + (x > 1 || x < -1 ? "s" : "") + " de Faim.",
                (x >= 0 ? "Guadagni " + x : "Perdi " + (-x)) + " Punt" + (x > 1 || x < -1 ? "i" : "o") + " Fame.",
                "أنتم " + (x >= 0 ? "فوز " + x : "تخسر " + (-x)) + "لا جوع");
    }
    public static String logHeroDeath(boolean deathByHunger) {
        if(deathByHunger) return lang("HERO DIED OF HUNGER", "LE HÉROS EST MORT DE FAIM", "L'EROE È MORTO DI FAME","البطل مات من الجوع");
        return lang("HERO IS DEAD", "LE HÉROS EST MORT", "L'EROE È MORTO","البطل ميت");
    }
    public static String logCantDropItem() {
        return lang("You can't drop the item here.", "Vous ne pouvez pas jeter l'objet ici.", "Non puoi lasciare l'oggetto qui.","لا يمكنك رمي العنصر هنا.");
    }
    public static String logNotEnoughMoney() {
        return lang("Not enough money.", "Pas assez d'argent.", "Denaro insufficiente.","مال غير كاف.");
    }
    public static String logInventoryFull() {
        return lang("The inventory is full!", "L'inventaire est plein !", "L'inventario è pieno!","المخزون ممتلئ!");
    }

    public static String merketTitle() {
        return lang("Merchant's market", "La boutique du marchand", "Il negozio del commerciante", "محل التاجر");
    }

    public static String logBuyOrSell(boolean isBuy, boolean verbalBase) {
        return verbalBase ? (isBuy ? lang("Buy", "Acheter", "Comprare", "يشترى") : lang("Sell", "Vendre", "Vendere", "باع")) :
                (isBuy ? lang("bought!", "acheté(e) !", "acquistato!", "تم شراؤها!") : lang("resold!", "revendu(e) !", "rivenduto!", "إعادة بيعها!"));
    }

    public static String logMoney() {
        return lang("coins", "pièces", "monete", "عملات معدنية");
    }

    public static String confirmDialog(boolean isBuy, boolean isTitle) {
        return isTitle ? (isBuy ? lang("Confirmation of purchase", "Confirmation d'achat", "Conferma di acquisto","تأكيد الشراء") : lang("Confirmation of sale", "Confirmation de vente", "Conferma di vendita","تأكيد البيع")) :
                (isBuy ? lang("Are you sure you want to buy an item that you cannot equip?", "Voulez-vous vraiment acheter un item dont vous ne pourrez vous équiper ?", "Sei sicuro di voler acquistare un oggetto che non puoi equipaggiare?","هل أنت متأكد أنك تريد شراء عنصر لا يمكنك تجهيزه؟") : lang("Are you sure you want to sell the item?", "Voulez-vous vraiment vendre l'objet ?", "Sei sicuro di voler vendere l'articolo?","هل أنت متأكد أنك تريد بيع العنصر؟"));
    }
    public static String logLevelUp() {
        return lang(">>> LEVEL UP +1! <<<", ">>> NIVEAU +1! <<<", ">>> LIVELLO +1! <<<",">>> المستوى +1! <<<");
    }
    public static String logTrap(int i) {
        return switch (i) {
            case 0 -> lang("You stepped on a burning trap!", "Vous avez marché sur un piège brûlant !", "Hai calpestato una trappola infiammata!","لقد داسوا على مصيدة ساخنة!");
            case 1 -> lang("An intense surprise freezes you!", "Une intense surprise vous gèle !", "Un'intensa sorpresa ti congela!","مفاجأة شديدة تجمدك!");
            case 2 -> lang("A poisonous trap!", "Un piège empoisonné !", "Una trappola velenosa!","فخ مسموم!");
            case 3 -> lang("You got caught by a teleporter trap!", "Un piège téléporteur !", "Una trappola ti teletrasporta!","فخ الناقل الآني!");
            case 4 -> lang("A bomb was planted here! [-15 HP]", "Une bombe était enterrée là ! [-15 PV]", "Una bomba è stata piazzata qui! [-15 HP]","هناك دفنت قنبلة! [-15 حياة]");
            default -> "";
        };
    }
    public static String logDie() {
        return lang("die", "meure ", "muore","موت");
    }
    public static String logFood() {
        return lang("Food", "Nourriture", "Cibo","طعام");
    }
    public static String logEffect(EntityState es) {
        String e = switch (es){
            case BURNT -> lang("Burning", "de brûlure", "bruciante","احتراق");
            case FROZEN -> lang("Frozing", "de gel", "congelante","الصقيع");
            case HEALED -> lang("Healing", "de guérison", "curativo","شفاء");
            case INVULNERABLE -> lang("Invulnerability", "d'invulnérabilité", "invulnerabilità","مناعة");
            case PARALYSED -> lang("Paralysis", "de paralysation", "paralisi","شلل");
            case POISONED -> lang("Poisoning", "d'empoisonnement", "avvelenamento","تسمم");
            case ENRAGED -> lang("Enraged", "d'enragement", "di rabbia","غاضب");
            default -> "";
        };
        return lang(e + " effect", "Effet " + e, "Effetto " + e, "تأثير" + e);
    }
    public static String logInventory() {return lang("Inventory", "Inventaire", "Inventario","...");}
    public static String logEquipped() {return lang("equipped", "équipé(e)", "in uso","...");}
    public static String logRejected() {return lang("rejected", "rejeté", "rifiutato", "...");}
    public static String logConsumed() {return lang("consumed", "consommé(e)", "consumato","...");}
    private static String logIsParalysedEffect(EntityType entityType) {
        return switch (entityType) {
            case HERO_WARRIOR -> " [-20% " + attack().replaceFirst(".$","") + "]";
            case HERO_ARCHER -> " [-2 " + range() + "]";
            case HERO_MAGE -> " [" + lang("Don't burn monsters anymore.", "Ne brûle plus les monstres." ,"Non brucia più mostri.","لم تعد تحرق الوحوش.") + "]";
            default -> "";
        };
    }
    public static String logEffect(AbstractEntity entity) {
        if (entity.getState() == EntityState.ENRAGED) {
            return lang("Rage makes ", "La rage rend ", "La rabbia rende ","يصنع داء الكلب") + entity
                    + lang(" stronger! [+10 ", " plus fort ! [+10 ", " più forte! [+10 ","اقوى ! [+10") + attack().replaceFirst(".$","") + "]";
        }
        return translate(entity.entityType) + switch (entity.getState()) {
            case BURNT -> lang(" is burning!", " brûle !", " sta bruciando!","يحرق!");
            case FROZEN -> lang(" is frozen!", " est gelé !", " è congelato!","مجمد!");
            case HEALED -> lang(" is healed!", " est soigné !", " è guarito!","أنيق!");
            case INVULNERABLE -> lang(" is invulnerable!", " est invulnérable !", " è invulnerabile!","هو منيع!");
            case PARALYSED -> lang(" is paralysed!", " est paralysé !", " è paralizzato!","مشلول!") + logIsParalysedEffect(entity.entityType);
            case POISONED -> lang(" is poisoned!", " est empoisonné !", " è avvelenato!","تسمم!");
            default -> "";
        };
    }
    public static String logBurnEffect(int amount) {
        return lang("The burn inflicts you " + amount + " damage.",
                "La brûlure vous inflige " + amount + " dégâts.",
                "La bruciatura ti infligge " + amount + " danni.",
                "يصيبك الحرق" + amount + " تلف.");
    }
    public static String logPoisonEffect(int amount) {
        return lang("You are suffering from poisoning. [-" + amount + " HP, -1 Hunger]",
                "Vous souffrez du poison. [-" + amount + " PV, -1 Faim]",
                "Soffri di avvelenamento. [-" + amount + " HP, -1 Fame]",
                "أنت تعاني من السم. [-" + amount + "الحياة ، -1 الجوع]");
    }
    public static String logHealEffect(int amount) {
        return lang("You are healed of " + amount + " HP.",
                "Vous êtes soigné de " + amount + " PV.",
                "Sei guarito di " + amount + " HP.",
                "أنت مهتم" + amount + "الحياة.");
    }
    public static String logItemCons(ConsumableTypes ct) {
        return switch (ct) {
            case HEALTH_POTION -> lang("+10% of your HP.", "+10% des PV", "+10% di HP", "+ 10٪ من الأرواح");
            case REGENERATION_POTION -> lang("Heal in duration", "Guérit sur la durée", "Guarito nel tempo", "تم علاجه بمرور الوقت");
            case TELEPORT_SCROLL -> lang("Go to Merchant room", "Téléporte chez le marchand", "Ti porta dal mercante", "يأخذك إلى التاجر");
            case DIVINE_BLESSING -> lang("Become invulnerable for a while", "Devenez invulnérable un moment", "Diventi invulnerabile per un po '", "كن محصنًا لبعض الوقت");
            case DRAGON_EXPLOSION -> lang("", "Inflige -25% à chaque monstre de la salle + Brûlure", "","");

        };
    }
    public static String logDragonExplo1() {
        return lang("Throwing the explosion in a room would have been more judicious...",
                "Lancer l'explosion dans une salle aurait été plus judicieux...",
                "Lanciare l'esplosione in una stanza sarebbe stato più giudizioso...",
                "كان من الممكن أن يكون إلقاء الانفجار في غرفة أكثر حكمة ...");
    }
    public static String logDragonExplo2() {
        return lang("The explosion frightened the merchant...",
                "L'explosion a fait sursauter le marchand...",
                "L'esplosione ha spaventato il mercante...",
                "الانفجار أرعب التاجر ...");
    }
    public static String logDragonExplo3(int nbr) {
        return nbr > 0 ?
                lang("The intense explosion impacted " + nbr + " monster" + (nbr == 1 ? "" : "s") + "!",
                "L'intense explosion a impactée " + nbr + " monstre" + (nbr == 1 ? "" : "s") + " !",
                "L'intensa esplosione ha avuto un impatto " + nbr + " mostr" + (nbr == 1 ? "o" : "i") + "!",
                " وحوش" + nbr + "الانفجار الشديد أصاب ") :
                lang("No monster was impacted by the explosion...",
                "Aucun monstre n'a été impacté par l'explosion...",
                "Nessun mostro è stato colpito dall'esplosione...",
                "لم يتأثر أي وحش بالانفجار ...");
    }



    // ===== Button Inventory Panel ===== //
    public static String buttonEquip(){return lang("Equip", "Équiper", "Usare", "...");}
    public static String buttonUnequip(){return lang("Unequip", "Déséquiper", "Lasciare", "...");}
    public static String buttonConsume() {return lang("Consume", "Consommer", "Consumare","...");}
    public static String buttonThrow() {return lang("Throw away", "Jeter", "Buttare via", "...");}



    // ===== TRANSLATIONS ===== //
    public static String translate(EntityType e) {
        return switch (e) {
            case HERO_WARRIOR -> warriorCL();
            case HERO_ARCHER -> archerCL();
            case HERO_MAGE -> mageCL();
            case ALLY_MERCHANT -> lang("MERCHANT", "MARCHAND", "MERCANTE","تاجر");
            case MONSTER_GOBLIN -> lang("GOBLIN", "GOBELIN", "GOBLIN","جوبيلين");
            case MONSTER_ORC -> lang("ORC", "ORC", "ORCO","مسخ");
            case MONSTER_SPIDER -> lang("SPIDER", "ARAIGNÉE", "RAGNO","عنكبوت");
            case MONSTER_WIZARD -> lang("WIZARD", "SORCIER", "STREGONE","ساحر");
            case MONSTER_BOSS -> lang("BOSS", "BOSS", "BOSS", "...");
        };
    }
    public static String translateHungerState(String state) {
        return switch (state) {
            case "Sated" -> lang("Sated", "Repu", "Sazio","متخم");
            case "Peckish" -> lang("Peckish", "Petit creux", "Poco affamato","جوفاء صغيرة");
            case "Hungry" -> lang("Hungry", "Affamé", "Affamato","جائع");
            case "Starving" -> lang("Starving", "Mort de faim", "Morto di fame","Mort de faim");
            default -> "";
        };
    }
    public static String translate(EntityState e) {
        return switch (e) {
            case BURNT -> lang("BURNT", "BRÛLÉ", "BRUCIATO","أحرق");
            case ENRAGED -> lang("ENRAGED", "ENRAGÉ", "INFURIATO","غاضب");
            case FROZEN -> lang("FROZEN", "GELÉ", "CONGELATO","مجمدة");
            case HEALED -> lang("HEALED", "SOIGNÉ", "RIGENERATO","اهتم ب");
            case INVULNERABLE -> lang("INVULNERABLE", "INVULNÉRABLE", "INVULNERABILE","ضار");
            case NEUTRAL -> lang("NEUTRAL", "NEUTRE", "NEUTRO","حيادي");
            case PARALYSED -> lang("PARALYSED", "PARALYSÉ", "PARALIZZATO","مشلولة");
            case POISONED -> lang("POISONED", "EMPOISONNÉ", "AVVELENATO","مسموم");
        };
    }
    public static String translate(EquipmentTypes equT) {
        return switch (equT) {
            case WOODEN_SWORD -> lang("Wooden Sword", "Épée en Bois", "Spada di Legno","Épée en Bois");
            case IRON_SWORD -> lang("Iron Sword", "Épée en Fer", "Spada di Ferro","سيف حديدي");
            case MAGIC_SWORD -> lang("Magic Sword", "Épée Magique", "Spada Magica","Épée Magique");
            case ICY_SWORD -> lang("Icy Sword", "Épée du Gel", "Spada di Ghiaccio","سيف فروست");
            case DRAGON_SWORD -> lang("Dragon Sword", "Épée du Dragon", "Spada del Drago","Épée du Dragon");

            case SHORT_BOW -> lang("Short Bow", "Arc Court", "Arco Corto","القوس القصير");
            case LONG_BOW -> lang("Long Bow", "Arc Long", "Arco Lungo","القوس الطويل");
            case DRAGON_BOW -> lang("Dragon Bow", "Arc du Dragon", "Arco del Drago","قوس التنين");

            case WOODEN_STAFF -> lang("Wooden Staff", "Sceptre en Bois", "Scettro di Legno","Sceptre en Bois");
            case MAGIC_STAFF -> lang("Magic Staff", "Sceptre Magique", "Scettro Magico","الصولجان السحري");
            case DRAGON_STAFF -> lang("Dragon Staff", "Sceptre du Dragon", "Scettro del Drago","صولجان التنين");

            case WOOD_SHIELD -> lang("Wooden Shield", "Bouclier en Bois", "Scudo di Legno","درع خشبي");
            case IRON_SHIELD -> lang("Iron Shield", "Bouclier en Fer", "Scudo di Ferro","درع الحديد");
            case DRAGON_SHIELD -> lang("Dragon Shield", "Bouclier du Dragon", "Scudo del Drago","درع التنين");

            case LEATHER_GAUNTLET -> lang("Leather Gauntlet", "Gantelet en Cuir", "Guanti in Pelle","القفاز الجلدي");
            case LEATHER_ARMBAND -> lang("Leather Armband", "Brassard en Cuir", "Bracciale in Pelle","سوار جلدي");
            case LEATHER_CHESTPLATE -> lang("Leather Chestplate", "Plastron en Cuir", "Pettorale in Pelle","ثدي الجلد");

            case MAGIC_HAT -> lang("Magic Hat", "Chapeau Magique", "Cappello Magico","قبعة سحرية");
            case MAGIC_TUNIC -> lang("Magic Tunic", "Tunique Magique", "Tunica Magica","سترة سحرية");
            case MAGIC_SPHERE -> lang("Magic Sphere", "Sphère Magique", "Sfera Magica","المجال السحري");
        };
    }
    public static String translate(ConsumableTypes consT) {
        return switch (consT) {
            case HEALTH_POTION -> lang("Health Potion", "Potion de Santé", "Pozione di Salute", "جرعة صحية");
            case REGENERATION_POTION -> lang("Regeneration Potion", "Potion de Régénération", "Pozione di Rigenerazione", "جرعة التجديد");
            case TELEPORT_SCROLL -> lang("Teleportation Scroll", "Parchemin de Téléportation", "Teletrasporto", "انتقل من تخاطر");
            case DIVINE_BLESSING -> lang("Divine Blessing", "Bénédiction Divine", "Benedizione Divina","نعمة إلهية");
            case DRAGON_EXPLOSION -> lang("Dragon Explosion", "Explosion Draconique", "Esplosione Draconica","انفجار شديد القسوة");
        };
    }
    public static String translate(Theme theme) {
        return switch (theme) {
            case DUNGEON -> lang("Dungeon", "Donjon", "Segreta", "...");
            case ISLANDS -> lang("Islands", "Îles", "Isole", "...");
            case FOREST -> lang("Forest", "Forêt", "Foresta", "...");
            case MERCHANT -> lang("Store", "Magasin", "Negozio", "...");
            case FINAL_BOSS -> lang("Final Boss", "Boss Final", "Boss Finale", "...");
        };
    }



    // ===== Help Menu Panel ===== //
    public static String history(){return lang("History", "Histoire", "Storie", "...");}
    public static String description(){return lang("Description", "Description", "Descrizione", "...");}
    public static String keys(){return lang("Keys", "Touches", "Tasti", "...");}
    public static String credits(){return lang("Credits", "Crédits", "Crediti", "...");}
    public static String item(){return lang("Item", "Objet", "Oggetto", "...");}
    public static String trap(){return lang("Trap", "Piège", "Trappola", "...");}
    public static String help(){return lang("Help", "Aide", "Aiuto", "...");}
    public static String enemies(){return lang("Enemies", "Ennemis", "Nemici", "...");}
    public static String heroes(){return lang("Heroes", "Héros", "Eroi", "...");}
    public static String aboutDescription(){
        return lang("<html><center>" +
                "<h1> Goal of the game </h1></center>" +
                "In this game you are in a world full of monsters<br>" +
                "that you have to kill.<br>" +
                "Each of them has a particularity:" +
                "<ul>" +
                "<li>Orcs are very strong</li>" +
                "<li>Spiders can poison you</li>" +
                "<li>Wizards cast spells that freeze you</li>" +
                "<li>Goblins aren't very strong and they flee if too weak</li>" +
                "</ul>" +
                "Your goal is to explore each stage to find the exit<br>" +
                "such that at the end you will face an awful <b>dragon</b>.<br>" +
                "Walking across the game you can find different items<br>" +
                "allowing you to increase your abilities, for instance<br>" +
                "<ul>" +
                "<li>Coins are useful let you buy items at the Merchant<br></li>" +
                "<li>Equipment allow you to increase your force or defense</li>" +
                "<li>Food restores a bit of HP and Hunger</li>" +
                "<li>Others with different effects</li>" +
                "</ul>" +
                "In the Merchant Room you can buy the items you want<br>" +
                "but each good has a price and you should have enough<br>" +
                "money to get it. You are also allowed to sell your<br>" +
                "items, but at a reduced price.<br>" +
                "Last thing before starting your Quest:<br>" +
                "Good luck and save the world!" +
                "</html>",
                "<html><center>" +
                "<h1> But du jeu </h1></center>" +
                "Dans ce jeu vous êtes dans un monde empli de monstres<br>" +
                "qu'il vous faudra tuer.<br>" +
                "Chacun d'entre eux possède une particularité :" +
                "<ul>" +
                "<li>Les orcs sont très forts</li>" +
                "<li>Les araignées empoisonnent</li>" +
                "<li>Les mages lancent des sorts de gel</li>" +
                "<li>Les gobelins ne sont pas très forts et fuient quand affaiblis</li>" +
                "</ul>" +
                "Votre objectif est d'explorer tous les étages pour en trouver la sortie<br>" +
                "jusqu'à ce que vous trouviez le terrible <b>dragon</b>.<br>" +
                "En avançant dans le jeu, vous trouverez différents objets<br>" +
                "vous permettant d'augmenter vos abilités, dans le détails : <br>" +
                "<ul>" +
                "<li>Les pièces sont utiles pour acheter chez le Marchand<br></li>" +
                "<li>Les équipements augmentent la force ou la défense</li>" +
                "<li>La nourriture restaure un peu de PV et de Faim</li>" +
                "<li>Autres avec des effets différents</li>" +
                "</ul>" +
                "Dans la salle du Marchant vous pouvez acheter les items que vous<br>" +
                "désirez à condition d'avoir assez d'argent pour l'obtenir. Vous<br>" +
                "avez aussi la possibilité de vendre vos items mais à un prix réduit.<br>" +
                "Dernière chose avant de commencer votre Quête :<br>" +
                "Bonne chance et sauvez le monde !" +
                "</html>",
                "<html><center>" +
                "<h1> Obiettivo del gioco </h1></center>" +
                "In questo gioco sei in mondo pieno di mostri<br>" +
                "che possono ucciderti con la loro forza.<br>" +
                "Ognuno di loro ha una caratteristica:" +
                "<ul>" +
                "<li>Gli orchi sono molto forti</li>" +
                "<li>I ragni ti avvelenano</li>" +
                "<li>I maghi fanno incantesimo paralizzanti</li>" +
                "<liI goblin non sono molto forti e fuggono se troppo deboli</li>" +
                "</ul>" +
                "Il tuo obiettivo è di esplorare ogni piano per trovare l'uscita<br>" +
                "cossicché alla fine tu possa trovare un temibile drago.<br>" +
                "Durante il gioco puoi trovare diversi oggetti <br>" +
                "che ti permettono di incrementare le tue abilité, per esempio<br>" +
                "<ul>" +
                "<li>Le monete sono utili per comprare oggetti dal mercante<br></li>" +
                "<li>Le armi ti permettono di aumentare la tua forza o la tua difesa</li>" +
                "<li>Il cibo rigenera un po' di HP o di fame</li>" +
                "<li>Altri con differenti effetti</li>" +
                "</ul>" +
                "Nella sala del mercante puoi comprare ciò che vuoi<br>" +
                "ma ogni cosa ha un prezzo e devi avere abbastanza soldi <br>" +
                "per potertelo permettere. Hai anche la possibilità di vendere i tuoi<br>" +
                "oggetti, ma a un prezzo ridotto.<br>" +
                "Ultima cosa prima di cominciare la missione:<br>" +
                "Buona fortuna e salva il mondo !" +
                "</html>",
                "...");
    }
    public static String charList(){
        return lang("<html><h1><center>List of characters</center></h1></html>",
                "<html><h1><center>Liste des personnages</center></h1></html>",
                "<html><h1><center>Lista dei personaggi</center></h1></html>",
                "...");
    }
    public static String itemList(){
        return lang("<html><h1><center>List of items</center></h1></html>",
                "<html><h1><center>Liste des objets</center></h1></html>",
                "<html><h1><center>Lista degli oggetti</center></h1></html>",
                "...");
    }
    public static String mainKeys(){
        return lang("<html><center><h1> Main keys </h1></center></html>",
                "<html><center><h1> Touches principales </h1></center></html>",
                "<html><center><h1> Tasti principali </h1></center></html>",
                "...");
    }
    public static String infoQ1(){
        return lang(
                "<html><center><h1> More info about Q key </h1>" +
                "<h2>Attack</h2><br>" +
                "When you press Q, you can see a gray zone (shadow) <br>" +
                "showing you your attack area and a red square.<br>" +
                "You can move this square with your move keys. <br>" +
                "If a monster is under your aim, the square become green & <br>" +
                "if you press Q again, this monster will be hit.<br>" +
                "If you want to quit attack mode, press Q" +
                "</center></html>",
                "<html><center><h1> Plus d'info sur la touche A </h1>" +
                "<h2>Attaque</h2><br>" +
                "Quand vous appuyez sur A, une zone grise (ombre) apparaît<br>" +
                "vous indiquant votre portée d'attaque ainsi qu'un carré rouge.<br>" +
                "Vous pouvez déplacer ce carré avec les touche de déplacement. <br>" +
                "Si un monstre est sous la cible, le carré devient vert et <br>" +
                "si vous appuyez à nouveau sur A, ce monstre sera attaqué.<br>" +
                "Si vous souhaitez sortir du mode attaque, appuyez sur A" +
                "</center></html>",
                "<html><center><h1> Maggiori informazioni sul tasto Q </h1>" +
                "<h2>Attacco</h2><br>" +
                "Quando premi Q, appare une zona grigia (ombra) <br>" +
                "che ti mostra il tuo raggio d'attacco e un quadrato rosso.<br>" +
                "Puoi spostare questo quadrato con i tasti d'azione <br>" +
                "Se un mostro è sotto la tua mira, il quadrato diventa verde & <br>" +
                "se premi di nuovo Q, questo mostro sarà colpito.<br>" +
                "Se vuoi usicre dalla modalità d'attacco, premi Q" +
                "</center></html>",
            "..."
        );
    }
    public static String infoQ2(){
        return lang(
                "<html><center><h2>Merchant</h2><br>" +
                        "When you are in the Merchant room, you can talk with<br>" +
                        "the Merchant setting the aim on him and clicking Q</center></html>",
                "<html><center><h2>Marchand</h2><br>" +
                        "Quand vous êtes dans la salle du Marchand, vous pouvez lui<br>" +
                        "parler en le ciblant puis en appuyant sur A</center></html>",
                "<html><center><h2>Mercante</h2><br>" +
                        "Quando sei dal Mercante, puoi parlare con lui<br>" +
                        "selezionando il tuo obiettivo su di lui e cliccando Q</center></html>",
                "..."
        );
    }
    public static String directions(Move m){
        return switch (m){
            case UP -> lang("Go up", "Monter", "Salire", "...");
            case DOWN -> lang("Go down", "Descendre", "Scendere", "...");
            case RIGHT -> lang("Go right", "Aller à droite", "Andare a destra", "...");
            case LEFT -> lang("Go left", "Aller à gauche", "Andare a sinistra", "...");
        };
    }
    public static String openTheInventory(){
        return lang(
                "Open the inventory",
                "Ouvrir l'inventaire",
                "Andare all'inventario",
                "..."
        );
    }
    public static String newGameSameHero(){
        return lang(
                "<html>Start new game<br>with the same hero</html>",
                "<html>Nouvelle partie<br>avec le même héros</html>",
                "<html>Nuova partita<br>con lo stesso eroe</html>",
                "..."
        );
    }
    public static String interactionReadBelow(){
        return lang("Interaction (read below)", "Interaction (lire ci-dessous)", "Interazione (leggi sotto)", "...");
    }
    public static String creditsText(){
        return lang(
                "<html><center>" +
                        "<h1> This project has been realised by </h1><br>" +
                        "<h2>BenAmara Adam<br>" +
                        "Fissore Davide<br>" +
                        "Garnier Quentin<br>" +
                        "Venturelli Antoine<br></h2>" +
                        "Period of realisation: first semester of 2021 <br>" +
                        "Why: TERD UE related to our L3 Info<br>" +
                        "Professor: Rémy Garcia<br>" +
                        "Programming language:" +
                        "</center></html>",
                "<html><center>" +
                        "<h1> Ce projet a été réalisé par </h1><br>" +
                        "<h2>BenAmara Adam <br>" +
                        "Fissore Davide <br>" +
                        "Garnier Quentin <br>" +
                        "Venturelli Antoine<br></h2>" +
                        "Période de réalisation : premier semestre de 2021 <br>" +
                        "Cadre : UE TERD pendant notre L3 Info<br>" +
                        "Professeur : Rémy Garcia<br>" +
                        "Language de programmation :" +
                        "</center></html>",
                "<html><center>" +
                        "<h1> Questo progetto è stato realizzato da </h1><br>" +
                        "<h2>BenAmara Adam <br>" +
                        "Fissore Davide <br>" +
                        "Garnier Quentin <br>" +
                        "Venturelli Antoine<br></h2>" +
                        "Periodo di realizzazione: primo semestre del 2021 <br>" +
                        "Obiettivo: per l'UE TERD durante la nostra L3 Info<br>" +
                        "Professore: Rémy Garcia<br>" +
                        "Linguaggio di programmazione:" +
                        "</center></html>",
                "..."
        );
    }
    public static String historyText(){
        return lang(
                "<html>" +
                    "<center><h1> That Time the Hero saved the Village </h1><br>" +
                    "Six hundred years ago, a terrible Evil awoke and ravaged the world.<br>" +
                    "No one could stop him and the hope was slowly fading, until the<br>" +
                    "arrival of a Hero with an unknown name who fought the Evil and<br>" +
                    "won, sending him back to the darkness where he came from.<br>" +
                    "Years passed and peace reigned for a long time, until the fatal day<br>" +
                    "when Evil reappeared.<br>" +
                    "He erected a grim fortress with his powers, near a small isolated village,<br>" +
                    "and is now preparing to finish what he had started six hundred years ago.<br>" +
                    "However, there is never a shadow without light: a brave defender of the<br>" +
                    "village appeared and now enters the dungeon to defeat, as in the legend,<br>" +
                    "the Evil himself.<br>" +
                    "<h3>Today, it's up to you to protect your village<br>" +
                    "against this ancestral threat.<br>" +
                    "Will you be able to become a Hero yourself by entering the legend?</h3></center>" +
                    "</html>",
                "<html>" +
                    "<center><h1> That Time the Hero saved the Village </h1><br>" +
                    "Il y a six cents ans, un Mal terrible se réveilla et ravagea le monde.<br>" +
                    "Nul ne pouvait s'y opposer et l'espoir s'éteignait peu à peu ;<br>" +
                    "puis vint alors un Héros au nom inconnu qui combattit le Mal et<br>" +
                    "en triompha, le renvoyant dans les ténèbres d'où il avait émergé.<br>" +
                    "Les années passèrent et la paix régna, longtemps, jusqu'au jour funeste<br>" +
                    "où le Mal réapparut.<br>" +
                    "Il érigea grâce à ses pouvoirs une forteresse maléfique, tout près <br>" +
                    "d'un petit village isolé, et se prépare désormais à finir ce qu'il<br>" +
                    "avait commencé il y a six cents ans.<br>" +
                    "Cependant, il n'y a jamais d'ombre sans lumière : un brave défenseur<br>" +
                    "du village est apparu et pénètre à présent dans le donjon afin d'y <br>" +
                    "vaincre, tout comme dans la légende, le Mal en personne.<br>" +
                    "<h3>C'est aujourd'hui à vous de protéger votre village<br>" +
                    "contre cette menace ancestrale.<br>" +
                    "Parviendrez-vous à devenir à votre tour un Héros en<br>" +
                    "entrant dans la légende ?</h3></center>" +
                    "</html>",
                "<html>" +
                    "<center><h1> That Time the Hero saved the Village </h1><br>" +
                    "Seicento anni fa, un Male terribile si svegliò e devastò il mondo.<br>" +
                    "Niente poteva opporsi e la speranza si spegneva a poco a poco;<br>" +
                    "venne poi un Eroe dal nome ignoto que combatté il male e<br>" +
                    "trionfò, ributtandolo nelle tenebre da cui era emerso.<br>" +
                    "Gli anni passarono e la pace regnò a lungo fino a quando, <br>" +
                    "un giorno funesto, il male riapparve.<br>" +
                    "Grazie ai suoi poteri erse une fortezza malefica, vicino a <br>" +
                    "un piccolo villaggio isolato e si prepara a terminare ciò che<br>" +
                    "aveva cominciato seicento anni fa.<br>" +
                    "Tuttavia, non c'è mai ombra senza luce: un bravo difensore<br>" +
                    "del villaggio è apparso e discende nella fortezza al fine di <br>" +
                    "vincere, comme dice la leggenda, il Male in persona.<br>" +
                    "<h3>A voi il compito di proteggere il vostro villaggio<br>" +
                    "contro questa minaccia ancestrale.<br>" +
                    "Riuscirate a diventare anche voi un Eroe<br>" +
                    "entrando nella leggenda?</h3></center>" +
                    "</html>",
                "<html>" +
                        "<center><h1> That Time the Hero saved the Village </h1><br>" +
                        "..." +
                        "..." +
                        "..." +
                        "</html>"
        );
    }
}