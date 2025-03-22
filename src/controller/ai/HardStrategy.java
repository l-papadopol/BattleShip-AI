/*
 * classe che implementa la logica di gioco difficile, AI classica con albero di decisione
 * (C) 2025 Papadopol Lucian Ioan - licenza CC BY-NC-ND 3.0 IT
 */
package controller.ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controller.AITurnStrategy;
import controller.handlers.ProjectileHandler;
import model.ModelInterface;
import model.entities.GridSquare;
import model.entities.Player;
import model.entities.Projectile;
import view.ViewInterface;
import view.components.Messages;

public class HardStrategy implements AITurnStrategy {

    private ModelInterface battle;
    private ViewInterface view;
    private ProjectileHandler projectileHandler;
    private Random random = new Random();

    public HardStrategy(ModelInterface battle, ViewInterface view, ProjectileHandler projectileHandler) {
        this.battle = battle;
        this.view = view;
        this.projectileHandler = projectileHandler;
    }

    @Override
    public boolean executeTurn(Player currentPlayer) {
        Player enemy = (currentPlayer == battle.getFirstPlayer()) ? battle.getSecondPlayer() : battle.getFirstPlayer();
        int dim = currentPlayer.getPersonalGrid().getSize();

        Point knownTarget = findPartialHitCell(enemy, dim);
        if (knownTarget != null) {
            while (true) {
                List<Point> adjacent = getAdjacentCandidates(knownTarget, enemy, dim);
                if (adjacent.isEmpty()) break;
                Point target = chooseBestCandidate(adjacent, enemy, dim);
                int type = (currentPlayer.getPowerfullProjectileQty() > 0) ? 2 : 1;
                boolean hit = doPcShot(currentPlayer, target, type);
                if (hit) knownTarget = target;
                else break;
            }
            return true;
        } else {
            Point target = huntMode(enemy, dim);
            double prob = estimateProbability(enemy, target, dim);
            int type = (prob > 0.5 && currentPlayer.getSpecialProjectileQty() > 0) ? 3 : 1;
            return doPcShot(currentPlayer, target, type);
        }
    }

    private Point findPartialHitCell(Player enemy, int dim) {
        GridSquare[][] squares = enemy.getPersonalGrid().getGridSquares();
        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                GridSquare cell = squares[x][y];
                if (cell.getIsHit() && cell.getDamageLevel() > 0 && cell.getDamageLevel() < cell.getMaxResistance()) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    private List<Point> getAdjacentCandidates(Point origin, Player enemy, int dim) {
        List<Point> result = new ArrayList<>();
        GridSquare[][] grid = enemy.getPersonalGrid().getGridSquares();
        int[][] deltas = { {1,0}, {-1,0}, {0,1}, {0,-1} };
        for (int[] d : deltas) {
            int nx = origin.x + d[0], ny = origin.y + d[1];
            if (nx >= 0 && nx < dim && ny >= 0 && ny < dim) {
                GridSquare g = grid[nx][ny];
                if (!g.getIsHit() && g.getDamageLevel() < g.getMaxResistance()) {
                    result.add(new Point(nx, ny));
                }
            }
        }
        return result;
    }

    private Point chooseBestCandidate(List<Point> candidates, Player enemy, int dim) {
        GridSquare[][] grid = enemy.getPersonalGrid().getGridSquares();
        int bestScore = -1;
        List<Point> best = new ArrayList<>();
        for (Point p : candidates) {
            int score = computePlacementScore(p.x, p.y, grid, dim);
            if (score > bestScore) {
                bestScore = score;
                best.clear();
                best.add(p);
            } else if (score == bestScore) {
                best.add(p);
            }
        }
        return best.get(random.nextInt(best.size()));
    }

    private int computePlacementScore(int x, int y, GridSquare[][] g, int dim) {
        int left = 0, right = 0, up = 0, down = 0;
        for (int i = x - 1; i >= 0 && !g[i][y].getIsHit(); i--) left++;
        for (int i = x + 1; i < dim && !g[i][y].getIsHit(); i++) right++;
        for (int i = y - 1; i >= 0 && !g[x][i].getIsHit(); i--) up++;
        for (int i = y + 1; i < dim && !g[x][i].getIsHit(); i++) down++;
        return Math.max(left + right + 1, up + down + 1);
    }

    private Point huntMode(Player enemy, int dim) {
        GridSquare[][] g = enemy.getPersonalGrid().getGridSquares();
        int bestScore = -1;
        List<Point> best = new ArrayList<>();
        for (int x = 0; x < dim; x++) {
            for (int y = 0; y < dim; y++) {
                if (!g[x][y].getIsHit()) {
                    int score = computePlacementScore(x, y, g, dim);
                    if (score > bestScore) {
                        bestScore = score;
                        best.clear();
                        best.add(new Point(x, y));
                    } else if (score == bestScore) {
                        best.add(new Point(x, y));
                    }
                }
            }
        }
        return best.isEmpty() ? new Point(random.nextInt(dim), random.nextInt(dim)) : best.get(random.nextInt(best.size()));
    }

    private double estimateProbability(Player enemy, Point p, int dim) {
        GridSquare[][] g = enemy.getPersonalGrid().getGridSquares();
        return (double) computePlacementScore(p.x, p.y, g, dim) / dim;
    }

    private boolean doPcShot(Player player, Point target, int type) {
        Projectile p = projectileHandler.makeProjectile(type, player);
        view.showMsg(Messages.pcShootMsg(target.x, target.y));
        boolean hit = battle.executeTurn(target, p);
        view.showMsg(hit ? Messages.pcHasHitMsg() : Messages.pcHasMissMsg());
        return hit;
    }
}
