/*
 * Name: Mohammad Mirabian
 * CruzID: mmirabia
 * File Name: GraphTest.c
 * File Purpose: file to test Graph.c
 */

#include<stdio.h>
#include<stdlib.h>
#include"List.h"
#include"Graph.h"


int main(int argc, char* argv[]){
  int i, n=8;
  List S = newList();
  Graph G = newGraph(n);
  Graph T=NULL, C=NULL;

  for(i=1; i<=n; i++) append(S, i);

  addArc(G, 1,2);
  addArc(G, 1,5);
  addArc(G, 2,5);
  addArc(G, 2,6);
  addArc(G, 3,2);
  addArc(G, 3,4);
  addArc(G, 3,6);
  addArc(G, 3,7);
  addArc(G, 3,8);
  addArc(G, 6,5);
  addArc(G, 6,7);
  addArc(G, 8,4);
  addArc(G, 8,7);
  printGraph(stdout, G);

  DFS(G, S);
  fprintf(stdout, "\n");
  fprintf(stdout, "x:  d  f  p\n");
  for(i=1; i<=n; i++){
    fprintf(stdout, "%d: %2d %2d %2d\n", i, getDiscover(G, i), getFinish(G, i), getParent(G, i));
  }
  fprintf(stdout, "\n");
  printList(stdout, S);
  fprintf(stdout, "\n");

  T = transpose(G);
  C = copyGraph(G);
  fprintf(stdout, "\n");
  printGraph(stdout, C);
  fprintf(stdout, "\n");
  printGraph(stdout, T);
  fprintf(stdout, "\n");

  DFS(T, S);
  fprintf(stdout, "\n");
  fprintf(stdout, "x:  d  f  p\n");
  for(i=1; i<=n; i++){
    fprintf(stdout, "%d: %2d %2d %2d\n", i, getDiscover(T, i), getFinish(T, i), getParent(T, i));
  }
  fprintf(stdout, "\n");
  printList(stdout, S);
  fprintf(stdout, "\n");
  //This concludes the Professors tests
  printf("This concludes the instructors tests-----------------------------\n");
  // The following tests a graph whose edges point back to its source
  freeList(&S);
  freeGraph(&G);
  freeGraph(&T);
  freeGraph(&C);
  // recomputes BFS with source vertex
  n = 20;
  S = newList();
  G = newGraph(n);
  for(i = 1; i<=n; i++){
    append(S, i);
    if(i%10 !=0) addArc(G,i, i);
  }
  addArc(G,9,11);
  addEdge(G,17,13);
  addArc(G,14,13);

  DFS(G, S);
  fprintf(stdout, "\n");
  fprintf(stdout, "x:  d  f  p\n");
  for(i=1; i<=n; i++){
    fprintf(stdout, "%d: %2d %2d %2d\n", i, getDiscover(G, i), getFinish(G, i), getParent(G, i));
  }
  fprintf(stdout, "\n");
  printList(stdout, S);
  fprintf(stdout, "\n");

  T = transpose(G);
  C = copyGraph(G);
  fprintf(stdout, "\n");
  printGraph(stdout, C);
  fprintf(stdout, "\n");
  printGraph(stdout, T);
  fprintf(stdout, "\n");

  DFS(T, S);
  fprintf(stdout, "\n");
  fprintf(stdout, "x:  d  f  p\n");
  for(i=1; i<=n; i++){
    fprintf(stdout, "%d: %2d %2d %2d\n", i, getDiscover(T, i), getFinish(T, i), getParent(T, i));
  }
  fprintf(stdout, "\n");
  printList(stdout, S);
  fprintf(stdout, "\n");
  freeList(&S);
  freeGraph(&G);
  freeGraph(&T);
  freeGraph(&C);
  return(0);
}
