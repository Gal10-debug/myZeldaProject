package com.gal.zelda;

public class PlayerInputSystem {
    public void update(WorldData world, float delta, InputState input) {
        for (int entity : world.playerControlled) {
            PositionComponent position = world.positions.get(entity);
            SpeedComponent speed = world.speeds.get(entity);
            FacingComponent facing = world.facings.get(entity);
            AnimationStateComponent animation = world.animationStates.get(entity);
            AttackComponent attack = world.attacks.get(entity);
            if (position == null || speed == null || facing == null || animation == null) {
                continue;
            }

            float moveX = 0f;
            float moveY = 0f;
            if (input.up) moveY += 1f;
            if (input.down) moveY -= 1f;
            if (input.left) moveX -= 1f;
            if (input.right) moveX += 1f;

            if (moveX != 0f || moveY != 0f) {
                float len = (float) Math.sqrt(moveX * moveX + moveY * moveY);
                position.x += (moveX / len) * speed.value * delta;
                position.y += (moveY / len) * speed.value * delta;
                setFacingFromMovement(facing, moveX, moveY);

                if (attack == null || attack.attackAnimationRemaining <= 0f) {
                    setState(animation, ActorState.WALKING);
                }
            } else if (attack == null || attack.attackAnimationRemaining <= 0f) {
                setState(animation, ActorState.IDLE);
            }
        }
    }

    private void setFacingFromMovement(FacingComponent facing, float moveX, float moveY) {
        if (Math.abs(moveX) > Math.abs(moveY)) {
            facing.x = Math.signum(moveX);
            facing.y = 0f;
        } else {
            facing.x = 0f;
            facing.y = Math.signum(moveY);
        }
    }

    private void setState(AnimationStateComponent animation, ActorState newState) {
        if (animation.state != newState) {
            animation.state = newState;
            animation.stateTime = 0f;
        }
    }
}
