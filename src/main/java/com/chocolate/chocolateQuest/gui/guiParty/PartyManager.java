package com.chocolate.chocolateQuest.gui.guiParty;

import java.util.ArrayList;
import com.chocolate.chocolateQuest.entity.EntityHumanBase;
import java.util.List;

public class PartyManager
{
    public static PartyManager instance;
    public List<EntityHumanBase> partyMembers;
    public List buttons;
    
    public PartyManager() {
        this.partyMembers = new ArrayList<EntityHumanBase>();
        this.buttons = new ArrayList();
    }
    
    public boolean addMember(final EntityHumanBase e) {
        if (this.countEntities() >= 10) {
            return false;
        }
        for (int i = 0; i < this.partyMembers.size(); ++i) {
            if (this.partyMembers.get(i) == e) {
                return true;
            }
        }
        this.partyMembers.add(e);
        return true;
    }
    
    public boolean removeMember(final EntityHumanBase e) {
        for (int i = 0; i < this.partyMembers.size(); ++i) {
            if (this.partyMembers.get(i) == e) {
                this.partyMembers.remove(i);
                if (this.buttons.size() > i) {
                    this.buttons.remove(i);
                }
            }
        }
        return true;
    }
    
    public List<EntityHumanBase> getMembers() {
        for (int i = 0; i < this.partyMembers.size(); ++i) {
            final EntityHumanBase human = this.partyMembers.get(i);
            if (human != null && human.isDead) {
                this.removeMember(human);
            }
        }
        return this.partyMembers;
    }
    
    public EntityHumanBase[] getEntitiesArray() {
        final int count = this.countEntities();
        int current = 0;
        final EntityHumanBase[] entities = new EntityHumanBase[count];
        for (int i = 0; i < this.partyMembers.size(); ++i) {
            final EntityHumanBase e = this.partyMembers.get(i);
            if (e != null) {
                entities[current] = e;
                ++current;
            }
        }
        return entities;
    }
    
    public int countEntities() {
        int count = 0;
        for (int i = 0; i < this.partyMembers.size(); ++i) {
            if (this.partyMembers.get(i) != null) {
                ++count;
            }
        }
        return count;
    }
    
    public void setButton(final int i, final Object button) {
        this.buttons.add(i, button);
    }
    
    public Object getButton(final int i) {
        if (i >= this.buttons.size()) {
            return null;
        }
        return this.buttons.get(i);
    }
    
    public void restart() {
        this.partyMembers.clear();
        this.buttons.clear();
    }
    
    static {
        PartyManager.instance = new PartyManager();
    }
}
