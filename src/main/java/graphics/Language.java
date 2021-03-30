package graphics;


import entity.AbstractEntity;
import entity.EntityState;
import entity.EntityType;
import graphics.window.GameWindow;
import items.AbstractItem;
import items.Collectables.ConsumableTypes;
import items.Collectables.EquipmentTypes;

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
        return lang(entity1 + " deals " + entity1.getAttack() + " damage to " + entity2 + ".",
                entity1 + " inflige " + entity1.getAttack() + " dégâts à " + entity2 + ".",
                entity1 + " infligge " + entity1.getAttack() + " danni a " + entity2 + ".",
                entity1 + " يلحق " + entity1.getAttack() + " ضرر لأ " + entity2 + ".");
    }
    public static String logCriticalHit(AbstractEntity entity1, AbstractEntity entity2, int atk) {
        return lang(entity1 + " deals " + atk + " damage to " + entity2 + " (critical hit)!",
                entity1 + " inflige " + atk + " dégâts à " + entity2 + " (coup critique) !",
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
    public static String logEquip(){return lang("Equip", "Équiper", "Usare", "...");}
    public static String logDisEquip(){return lang("Disequip", "Déséquiper", "Lasciare", "...");}
    public static String logConsume() {return lang("Consume", "Consommer", "Consumare","...");}
    public static String logThrow() {return lang("Throw away", "Jeter", "Buttare via", "...");}
    private static String logIsParalysedEffect(EntityType entityType) {
        return switch (entityType) {
            case HERO_WARRIOR -> " [-20%" + attack() + "]";
            case HERO_ARCHER -> " [-2 " + range() + "]";
            case HERO_MAGE -> " [" + lang("Don't burn monsters anymore.", "Ne brûle plus les monstres." ,"Non brucia più mostri.","لم تعد تحرق الوحوش.") + "]";
            default -> "";
        };
    }
    public static String logEffect(AbstractEntity entity) {
        if (entity.getState() == EntityState.ENRAGED) {
            return lang("Rage makes ", "La rage rend ", "La rabbia rende ","يصنع داء الكلب") + entity
                    + lang(" stronger! [+10 ", " plus fort ! [+10 ", " più forte! [+10 ","اقوى ! [+10") + attack() + "]";
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

            case LEATHER_ARMOR -> lang("Leather Armor", "Armure en Cuir", "Armatura di Cuoio","درع جلدي");

            case MAGIC_TUNIC -> lang("Magic Tunic", "Tunique Magique", "Tunica Magica","سترة سحرية");
        };
    }
    public static String translate(ConsumableTypes consT) {
        return switch (consT) {
            case HEALTH_POTION -> lang("Health Potion", "Potion de Santé", "Pozione di Salute", "جرعة صحية");
            case REGENERATION_POTION -> lang("Regeneration Potion", "Potion de Régénération", "Pozione di Rigenerazione", "جرعة التجديد");
            case TELEPORT_SCROLL -> lang("Teleportation Scroll", "Parchemin de Téléportation", "Teletrasporto", "انتقل من تخاطر");
            case DIVINE_BLESSING -> lang("Divine Blessing", "Bénédiction Divine", "Benedizione Divina","نعمة إلهية");
        };
    }

    public static String logItemCons(ConsumableTypes ct) {
        return switch (ct) {
            case HEALTH_POTION -> lang("+10% of your HP.", "+10% des PV", "+10% di HP", "+ 10٪ من الأرواح");
            case REGENERATION_POTION -> lang("Heal in duration", "Guérit sur la durée", "Guarito nel tempo", "تم علاجه بمرور الوقت");
            case TELEPORT_SCROLL -> lang("Go to Merchant room", "Téléporte chez le marchand", "Ti porta dal mercante", "يأخذك إلى التاجر");
            case DIVINE_BLESSING -> lang("Become invulnerable for a while", "Devenez invulnérable un moment", "Diventi invulnerabile per un po '", "كن محصنًا لبعض الوقت");
        };
    }
}