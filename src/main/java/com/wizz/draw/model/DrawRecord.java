/**
 * 
 */
package com.wizz.draw.model;

/**
 * @ClassName:     DrawRecord
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author:    Android_Robot
 * @date:        2019年4月28日 下午4:09:17
 *
 */
public class DrawRecord {

    private int id;
    private int drawId;
    private String playerId;
    private int score;
    private boolean win;
    public DrawRecord(){
        
    }
    
 
    public DrawRecord(int drawId, String playerId) {
        super();
        this.drawId = drawId;
        this.playerId = playerId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public int getDrawId() {
        return drawId;
    }
    public void setDrawId(int drawId) {
        this.drawId = drawId;
    }
    public String getPlayerId() {
        return playerId;
    }
    public void setPlayerId(String playerID) {
        this.playerId = playerID;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public boolean isWin() {
        return win;
    }
    public void setWin(boolean win) {
        this.win = win;
    }
   
}
