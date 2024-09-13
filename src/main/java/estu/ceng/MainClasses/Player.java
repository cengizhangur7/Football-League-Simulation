package estu.ceng.MainClasses;

import estu.ceng.Attributes.GoalkeepingAttributes;
import estu.ceng.Attributes.MentalAttributes;
import estu.ceng.Attributes.PhysicalAttributes;
import estu.ceng.Attributes.TechnicalAttributes;
import estu.ceng.Interface.Attributes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Player {
    private String name; // Oyuncunun adı
    private String position; // Oyuncunun pozisyonu
    private Attributes physicalAttributes; // Fiziksel yetenekler
    private Attributes technicalAttributes; // Teknik yetenekler
    private Attributes mentalAttributes; // Zihinsel yetenekler
    private Attributes goalkeepingAttributes; // Kalecilik yetenekleri (sadece kaleci için)

    public Player(String name, String position) {
        this.name = name;
        this.position = position;
        this.physicalAttributes = new PhysicalAttributes(); // Fiziksel yetenekleri başlatır
        this.technicalAttributes = new TechnicalAttributes(); // Teknik yetenekleri başlatır
        this.mentalAttributes = new MentalAttributes(); // Zihinsel yetenekleri başlatır
        if (position.equals("Goalkeeper")) {
            this.goalkeepingAttributes = new GoalkeepingAttributes(); // Kalecilik yeteneklerini başlatır
        }
        assignAttributesBasedOnPosition(); // Pozisyona göre yetenekleri ayarlar
    }

    // Oyuncunun pozisyonuna göre yetenekleri artırır
    private void assignAttributesBasedOnPosition() {
        Random rand = new Random();

        if (position.equals("Defender")) {
            increaseAttributes(rand, "Heading", "Marking", "Tackling", "Positioning", "Bravery", "Leadership", "Concentration");
        } else if (position.equals("Midfielder")) {
            increaseAttributes(rand, "Passing", "First Touch", "Vision", "Leadership", "Teamwork", "Bravery", "Natural Fitness", "Determination");
        } else if (position.equals("Attacker")) {
            increaseAttributes(rand, "Finishing", "Anticipation", "Composure", "Flair", "Dribbling", "Technique", "Acceleration", "Pace");
        } else if (position.equals("Goalkeeper")) {
            increaseAttributes(rand, "Aerial Reach", "Command of Area", "Communication", "Handling", "Kicking", "One on Ones", "Reflexes");
        }
    }

    // Belirli yetenekleri rastgele artıran metod
    private void increaseAttributes(Random rand, String... attributes) {
        for (String attribute : attributes) {
            int increaseValue = rand.nextInt(6) + 5; // 5 ile 10 arasında rastgele bir değer ekler.

            if (physicalAttributes.getAttributes().containsKey(attribute)) {
                ((PhysicalAttributes) physicalAttributes).setAttribute(attribute, increaseValue);
            } else if (technicalAttributes.getAttributes().containsKey(attribute)) {
                ((TechnicalAttributes) technicalAttributes).setAttribute(attribute, increaseValue);
            } else if (mentalAttributes.getAttributes().containsKey(attribute)) {
                ((MentalAttributes) mentalAttributes).setAttribute(attribute, increaseValue);
            } else if (goalkeepingAttributes != null && goalkeepingAttributes.getAttributes().containsKey(attribute)) {
                ((GoalkeepingAttributes) goalkeepingAttributes).setAttribute(attribute, increaseValue);
            }
        }
    }

    // Oyuncunun genel gücünü hesaplayan method
    public double getStrength() {
        return calculateStrength(
                physicalAttributes.getAttributes(),
                technicalAttributes.getAttributes(),
                mentalAttributes.getAttributes(),
                (goalkeepingAttributes != null) ? goalkeepingAttributes.getAttributes() : null
        );
    }

    // Yeteneklerin ortalamasını hesaplar ve gücü döndürür
    private double calculateStrength(Map<String, Integer>... attributes) {
        Map<String, Integer> combinedAttributes = new HashMap<>();
        for (Map<String, Integer> attr : attributes) {
            if (attr != null) {
                combinedAttributes.putAll(attr);
            }
        }

        double positionMultiplier = 1.0; // Pozisyona özgü yetenekler için katsayı
        double nonPositionMultiplier = 0.2; // Pozisyon dışı yetenekler için katsayı
        double total = 0.0;

        for (Map.Entry<String, Integer> entry : combinedAttributes.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            if (isRelevantAttribute(key)) {  // Pozisyona özgü özellikler
                total += value * positionMultiplier;
            } else {  // Pozisyon dışı özellikler
                total += value * nonPositionMultiplier;
            }
        }
        return (total / combinedAttributes.size())*5;
    }

    // Belirli bir pozisyon için ilgili özellikleri kontrol eder
    private boolean isRelevantAttribute(String attribute) {
        switch (position) {
            case "Defender":
                return Arrays.asList("First Touch", "Heading", "Marking", "Tackling", "Positioning", "Bravery", "Leadership",
                        "Concentration", "Jumping Reach", "Balance", "Strength", "Stamina", "Teamwork", "Determination").contains(attribute);

            case "Midfielder":
                return Arrays.asList("Heading", "Positioning", "Composure", "Flair", "Off the Ball", "Technique", "Passing",
                        "First Touch", "Vision", "Leadership", "Teamwork", "Bravery", "Natural Fitness", "Determination").contains(attribute);
            case "Attacker":
                return Arrays.asList("Finishing", "Anticipation", "Composure", "Flair", "Dribbling", "First Touch", "Passing",
                        "Technique", "Stamina", "Natural Fitness", "Balance", "Strength", "Acceleration", "Pace", "Decisions", "Long Shots", "Penalty Taking", "Work Rate").contains(attribute);
            case "Goalkeeper":
                return Arrays.asList("Aerial Reach", "Command of Area", "Communication", "Handling", "Kicking", "One on Ones",
                        "Punching", "Reflexes", "Anticipation", "Agility", "Jumping Reach", "Positioning", "Concentration").contains(attribute);
            default:
                return false;
        }
    }

    // Getter methodları
    public String getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public Attributes getPhysicalAttributes() {
        return physicalAttributes;
    }

    public Attributes getTechnicalAttributes() {
        return technicalAttributes;
    }

    public Attributes getMentalAttributes() {
        return mentalAttributes;
    }

    public Attributes getGoalkeepingAttributes() {
        return goalkeepingAttributes;
    }

    // Oyuncunun özelliklerini yazdıran method
    public void printAttributes() {
        // Başlıkları kalın yapmak için ANSI kodları.
        String boldStart = "\033[1m";
        String boldEnd = "\033[0m";

        // Oyuncu bilgilerini yazdırma
        System.out.printf("%s%-20s | %-15s | %-10s%s\n", boldStart, "Player: " + name, "Position: " + position, "Rating: " + String.format("%.2f", getStrength()), boldEnd);
        printLine(calculateLineLength());

        // Başlıkları yazdırma
        if (position.equals("Goalkeeper")) {
            System.out.printf("%-20s | %-20s | %-20s | %-20s\n", "Physical Attributes", "Technical Attributes", "Mental Attributes", "Goalkeeping Attributes");
        } else {
            System.out.printf("%-20s | %-20s | %-20s\n", "Physical Attributes", "Technical Attributes", "Mental Attributes");
        }
        printLine(calculateLineLength());

        int maxRows = Math.max(physicalAttributes.getAttributes().size(), Math.max(technicalAttributes.getAttributes().size(), mentalAttributes.getAttributes().size()));

        if (position.equals("Goalkeeper")) {
            maxRows = Math.max(maxRows, goalkeepingAttributes.getAttributes().size());
        }

        // Her satırı yazdırır
        for (int i = 0; i < maxRows; i++) {
            printAttributeRow(i);
        }
        printLine(calculateLineLength());
        System.out.println();
    }

    // Belirli bir satırı yazdırır
    private void printAttributeRow(int index) {
        String[] physicalKeys = physicalAttributes.getAttributes().keySet().toArray(new String[0]);
        String[] technicalKeys = technicalAttributes.getAttributes().keySet().toArray(new String[0]);
        String[] mentalKeys = mentalAttributes.getAttributes().keySet().toArray(new String[0]);
        String[] goalkeepingKeys = (goalkeepingAttributes != null) ? goalkeepingAttributes.getAttributes().keySet().toArray(new String[0]) : new String[0];

        String physicalAttr = (index < physicalKeys.length) ? physicalKeys[index].toLowerCase() + ": " + physicalAttributes.getAttributes().get(physicalKeys[index]) : "";
        String technicalAttr = (index < technicalKeys.length) ? technicalKeys[index].toLowerCase() + ": " + technicalAttributes.getAttributes().get(technicalKeys[index]) : "";
        String mentalAttr = (index < mentalKeys.length) ? mentalKeys[index].toLowerCase() + ": " + mentalAttributes.getAttributes().get(mentalKeys[index]) : "";

        if (position.equals("Goalkeeper")) {
            String goalkeepingAttr = (index < goalkeepingKeys.length) ? goalkeepingKeys[index].toLowerCase() + ": " + goalkeepingAttributes.getAttributes().get(goalkeepingKeys[index]) : "";
            System.out.printf("%-20s | %-20s | %-20s | %-20s\n", physicalAttr, technicalAttr, mentalAttr, goalkeepingAttr);
        } else {
            System.out.printf("%-20s | %-20s | %-20s\n", physicalAttr, technicalAttr, mentalAttr);
        }
    }

    // Çizgi uzunluğunu hesaplar
    private int calculateLineLength() {
        int lineLength = 0;
        String[] headers = {"Player: " + name, "Position: " + position, "Rating: " + String.format("%.2f", getStrength())};
        for (String header : headers) {
            lineLength = Math.max(lineLength, header.length());
        }

        if (position.equals("Goalkeeper")) {
            lineLength = Math.max(lineLength, "Physical Attributes".length() + "Technical Attributes".length() + "Mental Attributes".length() + "Goalkeeping Attributes".length() + 12);
        } else {
            lineLength = Math.max(lineLength, "Physical Attributes".length() + "Technical Attributes".length() + "Mental Attributes".length() + 8);
        }
        return lineLength;
    }

    // Çizgi çizer
    private void printLine(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
