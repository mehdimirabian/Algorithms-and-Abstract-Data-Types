/*
 * Name: Mohammad Mirabian
 * CruzID: mmirabia
 * File Name: FindComponents.c
 * File Purpose: Main program
 *
 */

#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include "Graph.h"
#define MAX_LEN 165

int main (int argc, char* argv[]){

  char tempString[MAX_LEN];

  if(argc != 3) {
    printf("Invalid number of ins");
    exit(1);
  }

  //this part opens both files
  FILE* in = fopen(argv[1], "r");
  FILE* out = fopen(argv[2], "w");

  // checkin if opening the files was successful
  if(in == NULL){
    printf("Unable to open file %s for reading\n", argv[1]);
    return 1;
  } else if (out == NULL){
    printf("Unable to open file %s for reading\n", argv[2]);
    return 1;
  }

  // read each line of in file, then count and print tokens
  fgets(tempString, MAX_LEN, in);
  int vertex = 0;
  sscanf(tempString, "%d", &vertex);
  List S = newList();
  for (int i = 1; i <= vertex; i++) append(S, i);
  // creats the graph
  Graph G = newGraph(vertex);
  while( fgets(tempString, MAX_LEN, in) != NULL) {
    int vertex1 = 0;
    int vertex2 = 0;
    sscanf(tempString, "%d %d", &vertex1, &vertex2);
    if(vertex1 == 0 && vertex2 == 0) break;
    addArc(G, vertex1, vertex2);
  }

  DFS(G, S);
  fprintf(out, "Adjacency list representation of G:\n");
  printGraph(out, G);

  Graph T = transpose(G);
  DFS(T, S);

  //counts the number of Scc
  int numScc = 0;
  for(moveFront(S); index(S) >= 0; moveNext(S)){
    if(getParent(T, get(S)) == NIL) numScc ++ ;
  }
  // puts the components into array list of size # of scc
  List Scc[numScc];
  int i = numScc;
  for(moveFront(S); index(S) >= 0; moveNext(S)){
    if(getParent(T, get(S)) == NIL){
      i--;
      Scc[i] = newList();
    }
    if(i == numScc) break;
    append(Scc[i], get(S));
  }

  //this part prints all the scc's
  fprintf(out, "\nG contains %d strongly connected components:", numScc);
  for(int j = 0; j < numScc; j++){
    fprintf(out, "\n");
    fprintf(out, "Component %d: ", j + 1);
    printList(out, Scc[j]);
    freeList(&(Scc[j]));
  }

  fprintf(out, "\n");
  freeGraph(&G);
  freeGraph(&T);
  freeList(&S);
  fclose(in);
  fclose(out);
  return(0);
}
