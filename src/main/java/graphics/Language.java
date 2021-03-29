package graphics;


import entity.AbstractEntity;
import entity.EntityState;
import entity.EntityType;
import graphics.window.GameWindow;
import items.AbstractItem;
import items.Collectables.ConsumableTypes;
import items.Collectables.EquipmentTypes;

public enum Language {
    EN, FR, IT;

    private static String lang(String en, String fr, String it) {
        Language l = GameWindow.language();
        return l == IT ? it : (l == FR ? fr : en);
    }


    // ===== Start menu buttons ===== //
    public static String newGame() {
        return lang("New Game", "Nouvelle Partie", "Nuova Partita");
    }
    public static String options() {
        return lang("Options", "Options", "Opzioni");
    }
    public static String exitGame() {
        return lang("Exit Game", "Quitter le Jeu", "Esci dal Gioco");
    }
    public static String back() {
        return lang("Back", "Retour", "Indietro");
    }
    public static String startTheQuest() {
        return lang("Start the Quest", "Commencer la Quête", "Inizia la Missione");
    }
    public static String validate() {
        return lang("Validate", "Valider", "Convalidare");
    }



    // ===== Chara selection menu ===== //
    public static String chooseYourSpeciality() {
        return lang("Choose your speciality", "Choisissez votre spécialité", "Scegli la tua specialità");
    }
    public static String warriorCL() {
        return lang("WARRIOR", "GUERRIER", "GUERRIERO"); /* CL is for 'CapsLock'  */
    }
    public static String archerCL() {
        return lang("ARCHER", "ARCHER", "ARCIERE"); /* CL is for 'CapsLock'  */
    }
    public static String mageCL() {
        return lang("MAGE", "MAGE", "MAGO"); /* CL is for 'CapsLock'  */
    }
    public static String warriorDescription() {
        String body1 = lang("The warrior deals great melee damage and has a large amount of HP.",
                "Le guerrier inflige de puissants dommages de mêlée et possède une large quantité de PV.",
                "Il guerriero infligge grandi danni di mischia e possiede una grossa quantità di HP.");
        String body2 = lang("In counterpart, he loses 1 Hunger Point for each attack.",
                "En contrepartie, il perd 1 Point de Faim à chaque attaque.",
                "Tuttavia, perde un Punto Fame a ogni attacco.");
        return "<html><p style=\"text-align: center;\">" + body1 + "<br />" + body2 + "</p></html>";
    }
    public static String archerDescription() {
        String body1 = lang("The archer deals good distance damage with his very long range but has few HP.",
                "L'archère inflige de bons dommages à distance avec une grande portée mais possède peu de PV.",
                "L'arciere infligge danni a distanza grazia alla sua grande portata, ma possiede pochi HP.");
        String body2 = lang("Each attack has a chance to deal more damage, inflict an effect... or miss the target.",
                "Chaque attaque a une chance d'infliger plus de dégâts, d'apposer un effet... ou de rater la cible.",
                "Ogni attacco ha la probabilità di essere più forte, di infliggere un effetto... o di mancare il bersaglio.");
        return "<html><p style=\"text-align: center;\">" + body1 + "<br />" + body2 + "</p></html>";
    }
    public static String mageDescription() {
        String body1 = lang("The mage deals moderate damage in a medium range.",
                "Le mage inflige des dégâts modérés dans une portée moyenne.",
                "Il mago infligge dei danni moderati con un raggio d'attacco medio.");
        String body2 = lang("His power lies in his ability to burn his opponents and heal himself slightly with each attack.",
                "Sa force réside dans sa capactié à brûler ses adversaires et se soigner légèrement à chaque attaque.",
                "La sua forza è la capacità di bruciare i suoi avversari e curarsi leggermente à ogni attacco.");
        return "<html><p style=\"text-align: center;\">" + body1 + "<br />" + body2 + "</p></html>";
    }



    // ===== Options menu ===== //
    public static String selectTheLanguage() {
        return lang("Select language", "Sélectionnez la langue", "Seleziona la lingua");
    }
    public static String gameSound() {
        return lang("Game sound", "Son du jeu", "Suono del gioco");
    }



    // ===== Stats panel (in game) ===== //
    public static String heroCP() {
        return lang("HERO", "HÉROS", "EROE");
    }
    public static String level() {
        return lang("Level", "Niveau ", "Livello");
    }
    public static String hp() {
        return lang("HP", "PV ", "HP");
    }
    public static String hunger() {
        return lang("Hunger", "Faim ", "Fame");
    }
    public static String attack() {
        return lang("Attack", "Attaque ", "Attacco");
    }
    public static String range() {
        return lang("Range", "Portée ", "Portata");
    }
    public static String pressIForInventory(boolean b) {
        return lang("Press [i] to " + (b? "open":"close") + " INVENTORY.",
                "Appuyez sur [i] pour " + (b? "ouvrir":"fermer") + " l'INVENTAIRE.",
                "Premere [i] per " + (b? "aprire":"chiudere") + " l'INVENTARIO.");
    }
    public static String money() {
        return lang("Money","Monnaie ", "Moneta");
    }



    // ===== Logs ===== //
    public static String logGainMoney(int value) {
        return lang("You have found: " + value + " coin" + (value>1? "s!": "!"),
                "Vous avez trouvé : " + value + " pièce" + (value>1? "s !": " !"),
                "Hai trovato: " + value + "monet" + (value>1? "a!": "e!"));
    }
    public static String logGainItem(AbstractItem i) {
        return lang("You have found: " + i + "!",
                "Vous avez trouvé : " + i + " !",
                "Hai trovato: " + i + "!");
    }
    public static String logLowerFloor() {
        return lang("You enter the lower floor...",
                "Vous pénétrez à l'étage inférieur...",
                "Si entra nel piano inferiore...");
    }
    public static String logNothingHappens() {
        return lang("Nothing happens.", "Rien ne se passe.", "Non succede niente.");
    }
    public static String logDealDamage(AbstractEntity entity1, AbstractEntity entity2) {
        return lang(entity1 + " deals " + entity1.getAttack() + " damage to " + entity2 + ".",
                entity1 + " inflige " + entity1.getAttack() + " dégâts à " + entity2 + ".",
                entity1 + " infligge " + entity1.getAttack() + " danni a " + entity2 + ".");
    }
    public static String logCriticalHit(AbstractEntity entity1, AbstractEntity entity2, int atk) {
        return lang(entity1 + " deals " + atk + " damage to " + entity2 + " (critical hit)!",
                entity1 + " inflige " + atk + " dégâts à " + entity2 + " (coup critique) !",
                entity1 + " infligge " + atk + " danni a " + entity2 + "(colpo critico)!");
    }
    public static String logMissedTarget() {
        return lang("Missed target...", "Cible manquée...", "Bersaglio mancato...");
    }
    public static String logModifyHunger(int x) {
        return lang("You " + (x >= 0 ? "gain " + x : "lose " + (-x)) + " Hunger Point" + (x > 1 || x < -1 ? "s" : "") + ".",
                "Vous " + (x >= 0 ? "gagnez " + x : "perdez " + (-x)) + " Point" + (x > 1 || x < -1 ? "s" : "") + " de Faim.",
                (x >= 0 ? "Guadagni " + x : "Perdi " + (-x)) + " Punt" + (x > 1 || x < -1 ? "i" : "o") + " Fame.");
    }
    public static String logHeroDeath(boolean deathByHunger) {
        if(deathByHunger) return lang("HERO DIED OF HUNGER", "LE HÉROS EST MORT DE FAIM", "L'EROE È MORTO DI FAME");
        return lang("HERO IS DEAD", "LE HÉROS EST MORT", "L'EROE È MORTO");
    }
    public static String logCantDropItem() {
        return lang("You can't drop the item here.", "Vous ne pouvez pas jeter l'objet ici.", "Non puoi lasciare l'oggetto qui.");
    }
    public static String logNotEnoughMoney() {
        return lang("Not enough money.", "Pas assez d'argent.", "Denaro insufficiente.");
    }
    public static String logInventoryFull() {
        return lang("The inventory is full!", "L'inventaire est plein !", "L'inventario è pieno!");
    }
    public static String logLevelUp() {
        return lang(">>> LEVEL UP +1! <<<", ">>> NIVEAU +1! <<<", ">>> LIVELLO +1! <<<");
    }
    public static String logTrap(int i) {
        return switch (i) {
            case 0 -> lang("You stepped on a burning trap!", "Vous avez marché sur un piège brûlant !", "Hai calpestato una trappola infiammata!");
            case 1 -> lang("An intense surprise freezes you!", "Une intense surprise vous gèle !", "Un'intensa sorpresa ti congela!");
            case 2 -> lang("A poisonous trap!", "Un piège empoisonné !", "Una trappola velenosa!");
            case 3 -> lang("You got caught by a teleporter trap!", "Un piège téléporteur !", "Una trappola ti teletrasporta!");
            case 4 -> lang("A bomb was planted here! [-15 HP]", "Une bombe était enterrée là ! [-15 PV]", "Una bomba è stata piazzata qui! [-15 HP]");
            default -> "";
        };
    }
    public static String logDie() {
        return lang("die", "meure ", "muore");
    }
    public static String logFood() {
        return lang("Food", "Nourriture", "Cibo");
    }
    private static String logIsParalysedEffect(EntityType entityType) {
        return switch (entityType) {
            case HERO_WARRIOR -> " [-20%" + attack() + "]";
            case HERO_ARCHER -> " [-2 " + range() + "]";
            case HERO_MAGE -> " [" + lang("Don't burn monsters anymore.", "Ne brûle plus les monstres." ,"Non brucia più mostri.") + "]";
            default -> "";
        };
    }
    public static String logEffect(AbstractEntity entity) {
        if (entity.getState() == EntityState.ENRAGED) {
            return lang("Rage makes ", "La rage rend ", "La rabbia rende ") + entity
                    + lang(" stronger! [+10 ", " plus fort ! [+10 ", " più forte! [+10 ") + attack() + "]";
        }
        return translate(entity.entityType) + switch (entity.getState()) {
            case BURNT -> lang(" is burning!", " brûle !", " sta bruciando!");
            case FROZEN -> lang(" is frozen!", " est gelé !", " è congelato!");
            case HEALED -> lang(" is healed!", " est soigné !", " è guarito!");
            case INVULNERABLE -> lang(" is invulnerable!", " est invulnérable !", " è invulnerabile!");
            case PARALYSED -> lang(" is paralysed!", " est paralysé !", " è paralizzato!") + logIsParalysedEffect(entity.entityType);
            case POISONED -> lang(" is poisoned!", " est empoisonné !", " è avvelenato!");
            default -> "";
        };
    }
    public static String logBurnEffect(int amount) {
        return lang("The burn inflicts you " + amount + " damage.",
                "La brûlure vous inflige " + amount + " dégâts.",
                "La bruciatura ti infligge " + amount + " danni.");
    }
    public static String logPoisonEffect(int amount) {
        return lang("You are suffering from poisoning. [-" + amount + " HP, -1 Hunger]",
                "Vous souffrez du poison. [-" + amount + " PV, -1 Faim]",
                "Soffri di avvelenamento. [-" + amount + " HP, -1 Fame]");
    }
    public static String logHealEffect(int amount) {
        return lang("You are healed of " + amount + " HP.",
                "Vous êtes soigné de " + amount + " PV.",
                "Sei guarito di " + amount + " HP.");
    }



    // ===== TRANSLATIONS ===== //
    public static String translate(EntityType e) {
        return switch (e) {
            case HERO_WARRIOR -> warriorCL();
            case HERO_ARCHER -> archerCL();
            case HERO_MAGE -> mageCL();
            case ALLY_MERCHANT -> lang("MERCHANT", "MARCHAND", "MERCANTE");
            case MONSTER_GOBLIN -> lang("GOBLIN", "GOBELIN", "GOBLIN");
            case MONSTER_ORC -> lang("ORC", "ORC", "ORCO");
            case MONSTER_SPIDER -> lang("SPIDER", "ARAIGNÉE", "RAGNO");
            case MONSTER_WIZARD -> lang("WIZARD", "SORCIER", "STREGONE");
        };
    }
    public static String translateHungerState(String state) {
        return switch (state) {
            case "Sated" -> lang("Sated", "Repu", "Sazio");
            case "Peckish" -> lang("Peckish", "Petit creux", "Poco affamato");
            case "Hungry" -> lang("Hungry", "Affamé", "Affamato");
            case "Starving" -> lang("Starving", "Mort de faim", "Morto di fame");
            default -> "";
        };
    }
    public static String translate(EntityState e) {
        return switch (e) {
            case BURNT -> lang("BURNT", "BRÛLÉ", "BRUCIATO");
            case ENRAGED -> lang("ENRAGED", "ENRAGÉ", "INFURIATO");
            case FROZEN -> lang("FROZEN", "GELÉ", "CONGELATO");
            case HEALED -> lang("HEALED", "SOIGNÉ", "RIGENERATO");
            case INVULNERABLE -> lang("INVULNERABLE", "INVULNÉRABLE", "INVULNERABILE");
            case NEUTRAL -> lang("NEUTRAL", "NEUTRE", "NEUTRO");
            case PARALYSED -> lang("PARALYSED", "PARALYSÉ", "PARALIZZATO");
            case POISONED -> lang("POISONED", "EMPOISONNÉ", "AVVELENATO");
        };
    }
    public static String translate(EquipmentTypes equT) {
        return switch (equT) {
            case WOODEN_SWORD -> lang("Wooden Sword", "Épée en Bois", "Spada di Legno");
            case IRON_SWORD -> lang("Iron Sword", "Épée en Fer", "Spada di Ferro");
            case MAGIC_SWORD -> lang("Magic Sword", "Épée Magique", "Spada Magica");
            case ICY_SWORD -> lang("Icy Sword", "Épée du Gel", "Spada di Ghiaccio");
            case DRAGON_SWORD -> lang("Dragon Sword", "Épée du Dragon", "Spada del Drago");

            case SHORT_BOW -> lang("Short Bow", "Arc Court", "Arco Corto");
            case LONG_BOW -> lang("Long Bow", "Arc Long", "Arco Lungo");
            case DRAGON_BOW -> lang("Dragon Bow", "Arc du Dragon", "Arco del Drago");

            case WOODEN_STAFF -> lang("Wooden Staff", "Sceptre en Bois", "Scettro di Legno");
            case MAGIC_STAFF -> lang("Magic Staff", "Sceptre Magique", "Scettro Magico");
            case DRAGON_STAFF -> lang("Dragon Staff", "Sceptre du Dragon", "Scettro del Drago");

            case WOOD_SHIELD -> lang("Wooden Shield", "Bouclier en Bois", "Scudo di Legno");
            case IRON_SHIELD -> lang("Iron Shield", "Bouclier en Fer", "Scudo di Ferro");
            case DRAGON_SHIELD -> lang("Dragon Shield", "Bouclier du Dragon", "Scudo del Drago");

            case LEATHER_ARMOR -> lang("Leather Armor", "Armure en Cuir", "Armatura di Cuoio");

            case MAGIC_TUNIC -> lang("Magic Tunic", "Tunique Magique", "Tunica Magica");
        };
    }
    public static String translate(ConsumableTypes consT) {
        return switch (consT) {
            case HEALTH_POTION -> lang("Health Potion", "Potion de Santé", "Pozione di Salute");
            case TELEPORT_SCROLL -> lang("Teleportation Sroll", "Parchemin de Téléportation", "Teletrasporto");
        };
    }

}