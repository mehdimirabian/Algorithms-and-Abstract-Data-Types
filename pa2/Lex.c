/*
 * Name: Mohammad Mirabian
 * CruzID: mmirabia
 * File Name: Lex.c
 */

#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#include "List.h"

#define MAXIMUM 160

//insertion sort
List insertsort(char** string, int length) {
	int i, j;
	List list = newList();

	if (length > 0)
		append(list, 0);
	for (j = 1; j < length; j++) {
		char *temp = string[j];
		int ind = j - 1;
		moveFront(list);
		for (i = 0; i < ind; i++) {
			moveNext(list);
		}
		//sorting
		while (ind > -1 && strcmp(temp, string[get(list)]) < 1) {
			ind--;
			movePrev(list);
		}

		if (index(list) == -1) {
			prepend(list, j);
		} else {
			insertAfter(list, j);
		}
	}

	return list;
}


char *strdup(const char *s) {
	char *d = malloc(strlen(s) + 1);
	if (d == NULL)
		return NULL;
	strcpy(d, s);
	return d;
}

//reads the file
char** read(FILE* in, int lines) {
	char** array = malloc(sizeof(char**) * lines);
	int i;
	char line[MAXIMUM];
	for (i = 0; i < lines; i++) {
		fgets(line, MAXIMUM, in);
		array[i] = strdup(line);
	}
	return array;
}

//frees heap entirely
void freeall(char** lines, int linecount, List list) {
	int i;
	for (i = 0; i < linecount; i++) {
		free(lines[i]);
	}
	free(lines);
	freeList(&list);
}

int main(int argc, char* argv[]) {
	int count = 0;
	char line[MAXIMUM];

	if (argc != 3) {
		printf("Invalid number of inputs");
		exit(1);
	}

	FILE* input = fopen(argv[1], "r");
	FILE* output = fopen(argv[2], "w");

	if (input == NULL) {
		printf("can't open file %s\n", argv[1]);
		return 1;
	} else if (output == NULL) {
		printf("can't open file %s\n", argv[2]);
		return 1;
	}

	// count tokens of input file
	while (fgets(line, MAXIMUM, input) != NULL) {
		count++;
	}

	// reset fgets
	fclose(input);
	input = fopen(argv[1], "r");
	char** lines = read(input, count);

	List list = insertsort(lines, count);

	// prints out the list
	for (moveFront(list); index(list) >= 0; moveNext(list)) {
		fprintf(output, "%s", lines[get(list)]);
	}

	//close files
	fclose(input);
	fclose(output);
	freeall(lines, count, list);
	return (0);
}
