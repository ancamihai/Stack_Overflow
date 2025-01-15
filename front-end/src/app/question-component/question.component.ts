import { Component, OnInit, Input } from '@angular/core';
import { TagService } from '../services/tag.service';
import { UserService } from '../services/user.service';
import { QuestionService } from '../services/question.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.scss']
})
export class QuestionComponent implements OnInit {

  @Input() question: any = {
    user: '',
    title: '',
    text: '',
    picture: '',
    tags: []
  };

  tagOptions: any[] = [];
  selectedTag: any = { tagId: -1, name: '' };
  selectedTagToRemove: any = { tagId: -1, name: '' };
  newTag: string = '';

  @Input() isAddQuestion: boolean = true;

  constructor(private router: Router, private tagService: TagService, private userService: UserService, private questionService: QuestionService) { }

  ngOnInit(): void {
    this.loadTags();
    this.selectedTag = this.tagOptions[0];
  }

  private loadTags(): void {
    this.tagService.getAllTags().subscribe(tags => {
        this.tagOptions =tags;
     });
  }

  addTag(selectedTag: any) {
    if (selectedTag) {
        const tagNotInQuestion = !this.question.tags.some((tag:any) => tag.name === selectedTag.name);
        if (tagNotInQuestion) {
          this.question.tags.push(selectedTag);
          this.selectedTag = { tagId: -1, name: '' };
        } else {
          console.log('Selected tag is already in question.tags:', selectedTag);
        }
     }
  }

  addNewTag(): void {
    if (this.newTag.trim() !== '') {
        const tagExists = this.question.tags.some((tag: any) => tag.name === this.newTag.trim());
        if (!tagExists) {
          const newTagToBeAdded = { tagId: null, name: this.newTag.trim() };
          this.tagService.addTag(newTagToBeAdded).subscribe(
              (response: any) => {
              this.loadTags();
              this.newTag = '';
              } ,
              (error: any) => {
              this.loadTags();
              this.newTag = '';
              } );
        } else {
          console.log('Tag already exists:', this.newTag.trim());
        }
        this.newTag = '';
      }
  }

  remove(selectedTagToRemove: any) {
    const index = this.question.tags.findIndex((tag: any) => tag.tagId === selectedTagToRemove.tagId);
    if (index !== -1) {
      this.question.tags.splice(index, 1);
    }
  }

  addQuestion() {
    this.question.user = this.userService.getCurrentUser();
    this.questionService.addQuestion(this.question).subscribe(
          (response) => {
          alert(response);
          this.router.navigate(['/questions/getAll']);
          },
          (error) => {
          alert(error.error.text);
          this.router.navigate(['/questions/getAll']);
          }
        );
  }

  updateQuestion(questionId: number, userId: number, updatedQuestion: any) {
    if (confirm('Are you sure you want to update this question?')) {
      if (userId == this.userService.getCurrentUser().userId || this.userService.getCurrentUser().position ==1) {
      this.questionService.updateQuestion(questionId, updatedQuestion, userId).subscribe(
             (response: any) => {
             alert(response);
             this.router.navigate(['/questions/getAll']);
             },
             (error: any) => {
             alert(error.error.text);
             this.router.navigate(['/questions/getAll']);
             }
        );
      } else {
        alert("You are not allowed to update this question!");
      }

    }
  }

  redirect() {
    this.router.navigate(['/questions/getAll']);
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const lastSlashIndex = file.name.lastIndexOf('/');
      const fileName = file.name.substring(lastSlashIndex + 1);
      console.log('File name split by /:', fileName);
      this.question.picture = fileName;
      }
  }

}
