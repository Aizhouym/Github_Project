package JoyfulMatch.common;

public class RankData {
    private String name;
    private int score;

    public RankData(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    
    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return this.score;
    }

}
